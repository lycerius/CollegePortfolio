<!--
Use this file as a skeleton for all webpages on this domain
-->
<?php
include_once("templates/template.php");
createHeader("compare.js", "compare.php"); #Include your javascript file as the param
?>
<center>

<?php
  createUsernameBox("uname1"); echo " Vs. ";createUsernameBox("uname2");
  createSubmitButton();
?>
<br />
<?php endPage(); ?>


<?php

function createTableRow($map, $tableheadername){

  echo "<thead>";
    echo "<td></td><td colspan=2 class='tableheader'>";
      echo $tableheadername;
    echo "</td>";
  echo "</thead>";
  $totals1 = 0;
  $totals2 = 0;
   foreach ($map as $key => $value) {

    $value1 = floatval(str_replace("hours","",str_replace(":","",str_replace(",","",$value[0]))));
    $value2 = floatval(str_replace("hours","",str_replace(":","",str_replace(",","",$value[1]))));
    if($key == "Deaths" || $key == "Deaths-Average"){
      $temp = $value1;
      $value1 = $value2;
      $value2 = $temp;
    }

    if($value1 > $value2){
      $totals1 += 1;
    }
    else if($value2 > $value1){
      $totals2 += 1;
    }
    else {
      $totals1 += 1;
      $totals2 += 1;
    }


    echo "<tr class='resultsTable'>";
      echo "<td>";
        echo $key;
      echo "</td>";
      echo "<td class='resultsTable'";
         if($value1 >= $value2){
           echo "style='color: #00ff00;;'>";
         }
         else{
           echo "style='color: #ff0000;'>";
         }
       echo str_replace("minutes", " minutes", str_replace("hours", " hours", $value[0]));
      echo "</td>";
      echo "<td class='resultsTable'";
         if($value2 >= $value1){
           echo "style='color: #00ff00;;'>";
         }
         else{
           echo "style='color: #ff0000;'>";
         }
       echo str_replace("minutes", " minutes", str_replace("hours", " hours", $value[1]));
      echo "</td>";
    echo "</tr>";
   }
   echo "<tr>
    <td class='resultsTable'>
    Totals
    </td>
    <td class='resultsTable'>";
    echo $totals1;
    echo "</td>
    <td class='resultsTable'>";
    echo $totals2;
    echo "
    </td>
   </tr>";

}

if(isset($_POST["combo"])){
  $leftSide = true;
  echo "<table width='80%'>";
  echo "<tr>";


  $decoded = json_decode($_POST["combo"], true);
  $keys    = $decoded["keys"];
  $statsForUser1 = $decoded[$keys[0]];
  $statsForUser2 = $decoded[$keys[1]];

  error_reporting(E_ERROR);
  echo "<td>";
  echo "</td>";
  echo "<td class='resultsTable'>";
  echo $keys[0];
  echo "</td>";
  echo "<td class='resultsTable'>";
  echo $keys[1];
  echo "</td>";
  $important = array(
    "Objective Kills"  => [$statsForUser1["ObjectiveKills"], $statsForUser2["ObjectiveKills"]],
    "Damage Done"      => [$statsForUser1["DamageDone"], $statsForUser2["DamageDone"]],
    "Eliminations"     => [$statsForUser1["Eliminations"], $statsForUser2["Eliminations"]],
    "Multikills"       => [$statsForUser1["Multikills"], $statsForUser2["Multikills"]],
    "Healing Done"     => [$statsForUser1["HealingDone"], $statsForUser2["HealingDone"]],
    "Deaths"           => [$statsForUser1["Deaths"], $statsForUser2["Deaths"]],
    "Games Won"        => [$statsForUser1["GamesWon"], $statsForUser2["GamesWon"]]
  );

  $most = array(
    "Eliminations"      => [$statsForUser1["Eliminations-MostinGame"],$statsForUser2["Eliminations-MostinGame"]],
    "Final Blows"       => [$statsForUser1["FinalBlows-MostinGame"],$statsForUser2["FinalBlows-MostinGame"]],
    "Damage Done"       => [$statsForUser1["DamageDone-MostinGame"],$statsForUser2["DamageDone-MostinGame"]],
    "Healing Done"      => [$statsForUser1["HealingDone-MostinGame"],$statsForUser2["HealingDone-MostinGame"]],
    "Objective Kills"   => [$statsForUser1["ObjectiveKills-MostinGame"],$statsForUser2["ObjectiveKills-MostinGame"]],
    "Solokills"         => [$statsForUser1["SoloKills-MostinGame"],$statsForUser2["SoloKills-MostinGame"]],
    "Multikills"        => [$statsForUser1["Multikill-Best"],$statsForUser2["Multikill-Best"]],
    "On Fire"           => [$statsForUser1["TimeSpentonFire-MostinGame"],$statsForUser2["TimeSpentonFire-MostinGame"]]
  );

  $average = array(
    "Eliminations"      => [$statsForUser1["Eliminations-Average"],$statsForUser2["Eliminations-Average"]],
    "Melee Final Blows" => [$statsForUser1["MeleeFinalBlows-Average"],$statsForUser2["MeleeFinalBlows-Average"]],
    "On Fire"           => [$statsForUser1["TimeSpentonFire-Average"],$statsForUser2["TimeSpentonFire-Average"]],
    "Solokills"         => [$statsForUser1["SoloKills-Average"],$statsForUser2["SoloKills-Average"]],
    "Objective Time"    => [$statsForUser1["ObjectiveTime-Average"],$statsForUser2["ObjectiveTime-Average"]],
    "Objective Kills"   => [$statsForUser1["ObjectiveKills-Average"],$statsForUser2["ObjectiveKills-Average"]],
    "Healing Done"      => [$statsForUser1["HealingDone-Average"],$statsForUser2["HealingDone-Average"]],
    "Final Blows"       => [$statsForUser1["FinalBlows-Average"],$statsForUser2["FinalBlows-Average"]],
    "Deaths"            => [$statsForUser1["Deaths-Average"],$statsForUser2["Deaths-Average"]],
    "Damage Done"       => [$statsForUser1["DamageDone-Average"],$statsForUser2["DamageDone-Average"]],
    "Eliminations"      => [$statsForUser1["Eliminations-Average"],$statsForUser2["Eliminations-Average"]]
  );

  createTableRow($average, "Average");
  createTableRow($most," Most in Game");
  createTableRow($important, "Basic Data");




  echo "</tr>";
  echo "</table>";
}
else if(isset($_GET["uname1"]) && isset($_GET["uname2"]) && isset($_GET["mode"])){
  echo "<script>compareStats('" . $_GET["uname1"] . "','". $_GET["uname2"] . "', '". $_GET["mode"] ."')</script>";
}

?>
</center>
