<!--
Use this file as a skeleton for all webpages on this domain
-->
<?php
include_once("templates/template.php");
createHeader("hero.js", "hero.php"); #Include your javascript file as the param
?>


<center>

    <?php
      createUsernameBox("uname");createSubmitButton();
      createCharacterPicker();

     ?>
</center>

<?php endPage(); ?>

<?php
error_reporting(E_ERROR);

function sanitizeKey($key){
  return str_replace("Spenton","Spent on",str_replace("Eliminationsper", "Eliminations per",str_replace("Mostin", "Most in",str_replace("-","",preg_replace("/([A-Z])/", " $1", $key)))));
}
function createTableRow($map, $tableheadername){

   echo "<tr>";
     echo "<td colspan=3 class='tableheader'>";
       echo $tableheadername;
     echo "</td>";
   echo "</tr>";

   foreach ($map as $key => $value) {
     if(is_null($value)) continue;
     echo "<tr class='resultsTable'>";
       echo "<td>";
         echo sanitizeKey($key);
       echo "</td>";
       echo "<td class='resultsTable'>";
        echo str_replace("hours", " hours", $value);
       echo "</td>";
     echo "</tr>";
   }


}
  //We got post data
  if(isset($_GET["uname"]) && isset($_GET["hero"]) && isset($_POST["heroname"]))
  {
    echo "<center> <table width=60%> </center>";
    $hero = $_POST["heroname"];
    $_POST["heroname"] = NULL;
    createTableRow($_POST,$hero);
    echo "</table>";

  }
  else if(isset($_GET["uname"]) && isset($_GET["hero"]) && isset($_GET["mode"])){
    echo "<script>getStatsForHero('" . $_GET["uname"] . "', '" . $_GET["hero"] .  "', '" . $_GET["mode"] . "')</script>";
  }
?>
