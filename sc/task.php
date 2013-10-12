<!DOCTYPE html>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="stylesheet.css"/>
		<title>Task Details</title>
	</head>
	<body>
		<div id="header">
			<p id="name">Project Social Computing - Task Details</p>
			<a href="/index.html"><p id="useraccount">Home</p></a>
		</div>
		<div class="left">
			<h4>Seller:</h4>
			<p>45 Jobs completed</p>
			<p>4.81 Average Rating</p>
		</div>
		<div class="right">
			<?php
			echo "<p>Jobid = " . $_GET["jobid"] . "</p>";
			?>
			<p>Please buy me 2 tickets for the concert of Linkin Park</p>
			<h4>Facts:</h4>
			<ul>
				<li>Number of tickets: 2</li>
				<li>Price per ticket: $45.00</li>
				<li>Reward: $4.00</li>
				<li>Due on: 12.11.2013</li>
			</ul>
		</div>
		<?php include('footer.php') ?>
	</body>
</html>