<!--
Use this file as a skeleton for all webpages on this domain
-->
<?php
include_once("templates/template.php");
createHeader("comparehero.js", "comparehero.php"); #Include your javascript file as the param
?>
<center>

<?php
  createUsernameBox("uname1"); echo " Vs. ";createUsernameBox("uname2");createSubmitButton();
  createCharacterPicker();

?>
<br />
<?php endPage(); ?>


<?php
function sanitizeKey($key){
  return str_replace("Spenton","Spent on",str_replace("Eliminationsper", "Eliminations per",str_replace("Mostin", "Most in",str_replace("-","",preg_replace("/([A-Z])/", " $1", $key)))));
}

function createTableRow($map, $tableheadername){

  echo "<thead>";
    echo "<td></td><td colspan=2 class='tableheader'>";
      echo $tableheadername;
    echo "</td>";
  echo "</thead>";

   foreach ($map as $key => $value) {

    $value1 = floatval(str_replace("hours","",str_replace(":","",str_replace(",","",$value[0]))));
    $value2 = floatval(str_replace("hours","",str_replace(":","",str_replace(",","",$value[1]))));
    if($key == "Deaths" || $key == "Deaths-Average"){
      $temp = $value1;
      $value1 = $value2;
      $value2 = $temp;
    }
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
         echo sanitizeKey($key);
       echo "</td>";
       echo "<td class='resultsTable'";
          if($value1 >= $value2){
            echo "style='color: #00ff00;'>";
          }
          else{
            echo "style='color: #ff0000;'>";
          }
        echo str_replace("minutes", " minutes", str_replace("hours", " hours", $value[0]));
       echo "</td>";
       echo "<td class='resultsTable'";
          if($value2 >= $value1){
            echo "style='color: #00ff00;'>";
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
  error_reporting(E_ERROR);
  $heroname = $_GET["hero"];
  $decoded = json_decode($_POST["combo"], true);
  $keys    = $decoded["keys"];
  $statsForUser1 = $decoded[$keys[0]][$heroname];
  $statsForUser2 = $decoded[$keys[1]][$heroname];
  $combination = array();
  foreach ($statsForUser1 as $key => $value) {
    $combination[$key] = array(
      $statsForUser1[$key], $statsForUser2[$key]
    );
  }

  echo "<td>";
  echo "</td>";
  echo "<td class='resultsTable'>";
  echo $keys[0];
  echo "</td>";
  echo "<td class='resultsTable'>";
  echo $keys[1];
  echo "</td>";

  createTableRow($combination, $heroname);




  echo "</tr>";
  echo "</table>";
}
else if(isset($_GET["uname1"]) && isset($_GET["uname2"]) && isset($_GET["hero"]) && isset($_GET["mode"])){
  echo "<script>compareStats('" . $_GET["uname1"] . "','". $_GET["uname2"] . "', '". $_GET["mode"] ."', '" . $_GET["hero"] . "')</script>";
}

?>
</center>
