<?php
  include_once("templates/template.php");
  createHeader("byname.js", "byname.php");
 ?>

 <center>
     <?php

      createUsernameBox("uname");
      createSubmitButton();
     ?>

       <br /><br />
 <?php endPage(); ?>

 <?php
 echo "<center>";
 function createTableRow($map, $tableheadername){

   echo "<thead>";
     echo "<td colspan=2 class='tableheader'>";
       echo $tableheadername;
     echo "</td>";
   echo "</thead>";
    foreach ($map as $key => $value) {
      echo "<tr class='resultsTable'>";
        echo "<td>";
          echo $key;
        echo "</td>";
        echo "<td class='resultsTable'>";
         echo str_replace("hours", " hours", $value);
        echo "</td>";
      echo "</tr>";
    }

 }



 if(isset($_POST["ObjectiveKills"])){
   $playerCard = json_decode($_POST["playerCard"], true);
   error_reporting(E_ERROR);
   createPlayerCard($playerCard);
    $important = array(
      "Objective Kills"  => $_POST["ObjectiveKills"],
      "Damage Done"      => $_POST["DamageDone"],
      "Eliminations"     => $_POST["Eliminations"],
      "Multikills"       => $_POST["Multikills"],
      "Healing Done"     => $_POST["HealingDone"],
      "Deaths"           => $_POST["Deaths"],
      "Games Won"        => $_POST["GamesWon"]
   );

   $most = array(
     "Eliminations"      => $_POST["Eliminations-MostinGame"],
     "Final Blows"       => $_POST["FinalBlows-MostinGame"],
     "Damage Done"       => $_POST["DamageDone-MostinGame"],
     "Healing Done"      => $_POST["HealingDone-MostinGame"],
     "Objective Kills"   => $_POST["ObjectiveKills-MostinGame"],
     "Solokills"         => $_POST["SoloKills-MostinGame"],
     "Multikills"        => $_POST["Multikill-Best"],
     "On Fire"           => $_POST["TimeSpentonFire-MostinGame"]
   );

   $average = array(
     "Eliminations"      => $_POST["Eliminations-Average"],
     "Melee Final Blows" => $_POST["MeleeFinalBlows-Average"],
     "On Fire"           => $_POST["TimeSpentonFire-Average"],
     "Solokills"         => $_POST["SoloKills-Average"],
     "Objective Time"    => $_POST["ObjectiveTime-Average"],
     "Objective Kills"   => $_POST["ObjectiveKills-Average"],
     "Healing Done"      => $_POST["HealingDone-Average"],
     "Final Blows"       => $_POST["FinalBlows-Average"],
     "Deaths"            => $_POST["Deaths-Average"],
     "Damage Done"       => $_POST["DamageDone-Average"],
     "Eliminations"      => $_POST["Eliminations-Average"]
   );
   echo "<table class='resultsTable' width=80%>";
   createTableRow($average, "Average Game");
   createTableRow($most, "Most in Game");
   createTableRow($important, "Basic Data");

   echo "</table>";
 }
 else if(isset($_GET["uname"]) && isset($_GET["mode"])){
   echo "<script>getHeroInformationByName('" . $_GET["uname"] . "','". $_GET["mode"] ."')</script>";
 }

 #We got a usernames
 if(isset($_GET["uname"])){
   $username = str_replace("#","-",urldecode($_GET["uname"]));
 }
 echo "</center>"
?>
