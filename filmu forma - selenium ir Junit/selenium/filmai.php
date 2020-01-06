<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Filmai</title>
	
	<style>
		<!-- Vartotojui isvedamos stilizuotos zinutes (sekmes/ nesekmes atveju) -->
        .msg-good {
        margin: 30px auto; 
        padding: 10px; 
        border-radius: 5px; 
        color: #3c763d; 
        background: #dff0d8; 
        border: 1px solid #3c763d;
        width: 10%;
        text-align: left;
        position: fixed;
     }
     
     .msg-bad {
        margin: 30px auto; 
        padding: 10px; 
        border-radius: 5px; 
        color: #ff0000; 
        background: #ffcccc; 
        border: 1px solid #ff0000;
        width: 10%;
        text-align: left;
        position: fixed;
     }
    </style>
	
</head>

<body>
    <h2>Filmų registracijos forma</h2>
    <div id="form">
        <form method="post">
			<input type="number" name="id" placeholder="Redagavimui/ trynimui">
            <br>
            <input type="text" name="pavadinimas" placeholder="Pavadinimas">
            <br>
            <input type="text" name="zanras" placeholder="Žanras">
            <br>
            <input type="text" name="rezisierius" placeholder="Režisierius">
            <br><br>
            <button type="submit" name="insert">Siųsti</button>
			<button type="submit" name="update">Redaguoti</button>
			<button type="submit" name="search">Ieškoti</button>
			<button type="submit" name="delete">Trinti</button>
        </form>
    </div>
</body>

</html>
<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "dbT19";

	$id_sablonas = "/[0-9]{1,8}/";
	$vardo_sablonas = "/[A-Za-z]{3,30}/";
	$pasto_sablonas = "/[A-Za-z]{3,30}/";
	$teksto_sablonas = "/[A-Za-z]{3,30}/";

// Create connection

$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection

if ($conn->connect_error)
	{
	die("Connection failed: " . $conn->connect_error);
	}

// "insert" mygtukui

if (isset($_POST["insert"]))
	{
	$vardas = $_POST['pavadinimas'];
	$email = $_POST['zanras'];
	$text = $_POST['rezisierius'];
	$ip = $_SERVER['REMOTE_ADDR'];

	if (preg_match($vardo_sablonas, $vardas) && preg_match($pasto_sablonas, $email) && preg_match($teksto_sablonas, $text))
		{
		$sql = "INSERT INTO vardas_pavarde(ip, vardas, epastas, zinute) VALUES('$ip', '$vardas', '$email' , '$text')";
		if ($conn->query($sql) === TRUE)
			{
			echo '<p> <div class="msg-good"> Duomenys irasyti sekmingai </div>';
			}
		  else
			{
			echo '<p> <div class="msg-bad"> Duomenu irasymo klaida </div>';
			}
		}
	  else
		{
		echo '<p> <div class="msg-bad"> Blogai ivesti duomenys kuriant nauja irasa </div>';
		}
	}//insert
	
	//delete mygtukui
	if (isset($_POST['delete'])) {
		$id = $_POST['id'];
		if (preg_match($id_sablonas, $id)){
			$sql = "DELETE FROM vardas_pavarde WHERE id='$id'";
			if ($conn->query($sql) === TRUE) {
				echo '<p> <div class="msg-good"> Irasas istrintas sekmingai </div>';
			} else {
				echo '<p> <div class="msg-bad"> Duomenu istrynimo klaida </div>';
			}
		} else { 
			echo '<p> <div class="msg-bad"> Blogai ivestas id trinant irasa </div>';
		}
	}
	
	// edite mygtukui
	if (isset($_POST['update'])) {
		$id = $_POST['id'];
		$vardas = $_POST['pavadinimas'];
		$email = $_POST['zanras'];
		$text = $_POST['rezisierius']; 
		$ip = $_SERVER['REMOTE_ADDR'];
		if (preg_match ($id_sablonas, $id) && preg_match($vardo_sablonas, $vardas) 
			&& preg_match($pasto_sablonas, $email) && preg_match($teksto_sablonas, $text)){
		
				$sql = "UPDATE vardas_pavarde SET vardas='$vardas', epastas='$email', zinute='$text' WHERE id='$id'";
				if ($conn->query($sql) === TRUE) {
					echo '<p> <div class="msg-good"> Irasas redaguotas sekmingai </div>';
				} else {
					echo '<p> <div class="msg-bad"> Duomenu redagavimo klaida </div>';
				}
		} else { 
			echo '<p> <div class="msg-bad"> Blogai ivesti duomenys redaguojant irasa </div>';
		}
	}
		
	// search mygtukui
	if (isset($_POST['search'])) {
		$vardas = $_POST['pavadinimas'];		
		if (empty($vardas)) {
			$sql = "SELECT id, data, vardas, epastas, zinute FROM vardas_pavarde";
		} else{
			$sql = "SELECT id, data, vardas, epastas, zinute FROM vardas_pavarde WHERE vardas LIKE '%$vardas%'";
		}
		$result = $conn->query($sql);
		echo "<br>";
        echo "<table border=1>";		
		if ($result->num_rows >0) {
			echo "<tr>
			        <th>NR</th>
					<th>ID</th>
					<th>DATA</th>
					<th>PAVADINIMAS</th> 
					<th>ŽANRAS</th>
					<th>REŽISIERIUS</th>
				  </tr>";
				  $nr= 1;
			while($row= $result->fetch_assoc()){
				echo "<tr><td> " . $nr++. "</td><td> " . $row["id"]. "</td> <td>" . $row["data"]. "</td> <td>" . $row["vardas"]. 
				  "</td> <td>" . $row["epastas"]. "</td><td> " . $row["zinute"]. "</td> </tr>";
			}
		} else{
			echo '<p> <div class="msg-bad"> Nieko nerasta pagal ivesta paieska </div>';
		}
		echo "</table>";
	}

$conn->close();
?>