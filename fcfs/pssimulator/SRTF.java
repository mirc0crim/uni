package pssimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class SRTF implements pssimulator.Kernel {
	private final List<Process> runQueue;
	private final Map<String, IODevice> ioList;
	private final ArrayList<Process> finishedList;
	private int saves;

	public SRTF() {
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
		simulator.schedulePreemptionInterrupt(0);
	}

	@Override
	public void systemCallIORequest(String deviceID, long timer,
			Simulator simulator) {
		Process p = runQueue.remove(0);
		IODevice device = ioList.get(deviceID);
		device.addToWaitingQueue(p, timer);
		saves++;
	}

	@Override
	public void systemCallProcessTermination(long timer, Simulator simulator) {
		Process p = runQueue.remove(0);
		p.finish(timer);
		finishedList.add(p);
	}

	@Override
	public void interruptIODevice(String deviceID, long timer,
			Simulator simulator) {
		IODevice device = ioList.get(deviceID);
		Process p = device.pollWaitingProcess(timer);
		p.startedWaiting(timer);
		runQueue.add(p);
		reorderReadyQueue(simulator);
	}

	@Override
	public void interruptPreemption(long timer, Simulator simulator) {
		reorderReadyQueue(simulator);
	}

	@Override
	public String running(long timer, Simulator simulator) {
		if (!runQueue.isEmpty()) {
			Process p = runQueue.get(0);
			if (p.isWaiting()) {
				p.stopedWaiting(timer);
			}
			return p.getProcessID();
		} else {
			return "Idle";
		}
	}

	private void reorderReadyQueue(Simulator simulator) {
		ProcessComparer<Process> comp = new ProcessComparer<Process>(simulator);
		Collections.sort(runQueue, comp);
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
			isWaiting = true;
			waitingAt = timer;
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

	private class ProcessComparer<T> implements Comparator<Process> {
		private final Simulator simulator;

		public ProcessComparer(Simulator simulator) {
			this.simulator = simulator;
		}

		@Override
		public int compare(Process p1, Process p2) {
			if (simulator.queryBurstRemainingTime(p1.getProcessID()) < simulator
					.queryBurstRemainingTime(p2.getProcessID())) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}
