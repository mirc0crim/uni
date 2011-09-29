package pssimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class RR implements pssimulator.Kernel {
	private final int timeUnit = 2;
	private final Queue<Process> runQueue;
	private final Map<String, IODevice> ioList;
	private final ArrayList<Process> finishedList;
	private int saves;

	public RR() {
		runQueue = new LinkedList<Process>();
		ioList = new HashMap<String, IODevice>();
		finishedList = new ArrayList<Process>();
		saves = 0;
	}

	@Override
	public void systemCallInitIODevice(String deviceID, Simulator simulator) {
		ioList.put(deviceID, new IODevice());
	}

	@Override
	public void systemCallProcessCreation(String processID, long timer,
			Simulator simulator) {
		Process p = new Process(processID, timer);
		runQueue.add(p);
		preemptionInterupt(simulator);
	}

	@Override
	public void systemCallIORequest(String deviceID, long timer,
			Simulator simulator) {
		Process p = runQueue.remove();
		IODevice d = ioList.get(deviceID);
		d.addToWaitingQueue(p, timer);
		saves++;
		preemptionInterupt(simulator);
	}

	@Override
	public void systemCallProcessTermination(long timer, Simulator simulator) {
		Process p = runQueue.remove();
		p.finish(timer);
		finishedList.add(p);
		preemptionInterupt(simulator);
	}

	@Override
	public void interruptIODevice(String deviceID, long timer,
			Simulator simulator) {
		IODevice d = ioList.get(deviceID);
		Process p = d.pollWaitingProcess(timer);
		if (runQueue.size() > 1) {
			Process firstProcess = runQueue.remove();
			firstProcess.startedWaiting(timer);
			runQueue.add(firstProcess);
		}
		runQueue.add(p);
		p.startedWaiting(timer);
		preemptionInterupt(simulator);
	}

	private void preemptionInterupt(Simulator simulator) {
		if (runQueue.size() > 1) {
			simulator.schedulePreemptionInterrupt(timeUnit);
		}
	}

	@Override
	public void interruptPreemption(long timer, Simulator simulator) {
		if (!runQueue.isEmpty()) {
			Process p = runQueue.peek();
			if (simulator.queryBurstRemainingTime(p.getProcessID()) >= 0) {
				runQueue.remove();
				p.startedWaiting(timer);
				runQueue.add(p);
			}
		}
		preemptionInterupt(simulator);
	}

	@Override
	public String running(long timer, Simulator simulator) {
		if (!runQueue.isEmpty()) {
			Process p = runQueue.peek();
			if (p.isWaiting()) {
				p.stopedWaiting(timer);
			}
			return p.getProcessID();
		} else {
			return "Idle";
		}
	}

	@Override
	public void terminate(long timer, SimulatorStatistics simulator) {
		int numberOfProcesses = finishedList.size();
		int wmt = 0;
		int att = 0;
		for (Process p : finishedList) {
			wmt += p.getWaitingTime();
			att += p.processDuration();
		}
		wmt = wmt / numberOfProcesses;
		att = att / numberOfProcesses;
		simulator.formatStatistics(System.out, simulator.getSystemTime(),
				simulator.getUserTime(), simulator.getIdleTime(),
				simulator.getSystemCallsCount(), saves, wmt, att);
	}

	private class Process {
		private final String pID;
		private final long startedAt;
		private long finishedAt;
		private long waitingAt = 0;
		private long waitingTime = 0;
		private boolean isWaiting = true;

		public Process(String processID, long createdAt) {
			pID = processID;
			startedAt = createdAt;
		}

		public String getProcessID() {
			return pID;
		}

		public void finish(long timer) {
			finishedAt = timer;
		}

		public void startedWaiting(long timer) {
			waitingAt = timer;
			isWaiting = true;
		}

		public void stopedWaiting(long timer) {
			waitingTime += (timer - waitingAt);
			isWaiting = false;
			waitingAt = 0;
		}

		public long getWaitingTime() {
			return waitingTime;
		}

		public long processDuration() {
			if (finishedAt < 0) {
				return 0;
			}
			return (finishedAt - startedAt);
		}

		public boolean isWaiting() {
			return isWaiting;
		}

	}

	private class IODevice {
		private final Queue<Process> waitingQueue;

		public IODevice() {
			waitingQueue = new LinkedList<Process>();
		}

		public void addToWaitingQueue(Process p, long timer) {
			waitingQueue.add(p);
		}

		public Process pollWaitingProcess(long timer) {
			Process p = waitingQueue.remove();
			return p;
		}
	}
}
