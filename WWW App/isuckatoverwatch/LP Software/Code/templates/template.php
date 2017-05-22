<?php
  function createHeader($relatedJS, $relatedPHP){
    $header = "<!DOCTYPE html>
    <html>
    <head>
    <meta charset='utf-8' />
    <title>Do I suck at: Overwatch</title>
    <link rel='stylesheet' type='text/css' href='css/reset.css' />
    <link rel='stylesheet' type='text/css' href='css/main.css'  />
    <link rel='shortcut icon' type='image/x-icon' href='images/favicon.ico' />
    <script src='https://code.jquery.com/jquery-3.1.1.min.js'></script>";
    if(!is_null($relatedJS)){$header = $header . "<script src='javascript\\" . $relatedJS . "'></script>";}
    $header = $header . "
    <script src='javascript\\api.js'></script>
    </head>
    <body>
    <div id='banner' style='background-image: url(images/maxresdefault.jpg); background-position: -30px;'>
      <center>
        <h1>Do you suck at</h1><br />
        <image src='images\overwatch_logo_by_feeerieke-da4xuzp_fix.png' height='256px' width='50%'/>
      </center>
    </div>
    <ul>
      <li><a href='byname.php'>Search By Name</a></li>
      <li><a href='compare.php'>Compare By Name</a></li>
      <li><a href='hero.php'>Search By Character</a></li>
      <li><a href='comparehero.php'>Compare By Character</a></li>
      <form id='nameparameters' method='get' action='" . $relatedPHP . "'>
      <li class = 'modeToggle'>
        <input type='radio'  name='mode' value='quickplay' checked/> Quick Play
      </li>
      <li class = 'modeToggle'>
        <input type='radio'  name='mode' value='competitive' ";
          if(isset($_GET["mode"]) && $_GET["mode"] == "competitive")
          {
            $header .= "checked";
          }
        $header .= "/> Competitive
      </li>
    </ul>
    <br  />
    ";
    echo $header;
  }

  function endPage(){echo "<center>
    <span id='loaderPlacement'></span>
    </center></body></html>";
  echo "</form>";}

  function createUsernameBox($id){
    echo "<input type=\"text\" id='" . $id . "' class=\"materialText\" name='" . $id . "' placeholder=\"Battletag#Number\" value='";
    if(isset($_GET[$id]))
    {
      echo $_GET[$id];
    }
    else{
      echo "";
    }
    echo "'/>";
  }
  function createModePicker(){

  }
  function createSubmitButton(){
    echo "<input type='submit' value='Search' class='materialButton' />";
  }

  function createCharacterPicker(){
    echo "<div id='heroSelector'>";
    echo "<center><span class='heroPicker'>Choose your Hero</span></center><br />";
    $allCharacters = scandir("css/Characters");
    for ($i=2; $i < sizeof($allCharacters); $i++) {
      $trueName = substr($allCharacters[$i], 0,strpos($allCharacters[$i], "."));
      echo "
      <label>
        <input type='radio'  name='hero' value='" . $trueName . "' class='heroPicker' ";
        if(isset($_GET["hero"]) && $_GET["hero"] == $trueName){
          echo "checked='checked'";
        }
        echo "/><img src='css/Characters/" . $allCharacters[$i] . "' class='heroPicker' /></label>";

    }
    echo "</div>";
  }

  function createPlayerCard($playerCardJSON){
    $avatar = $playerCardJSON["avatar"];
    $tier   = $playerCardJSON["tier-img"];
    $name   = $playerCardJSON["username"];
    $rank   = $playerCardJSON["comp-rank"];
    $compWins = $playerCardJSON["comp-wins"];
    $compLoss = $playerCardJSON["comp-loss"];
    $compPlayed = $playerCardJSON["comp-played"];
    $compTie    = $compPlayed-$compWins-$compLoss;
    $quickWins = $playerCardJSON["quick-wins"];
    $level    = $playerCardJSON["level"];
    $playTimeComp = $playerCardJSON["comp-pt"];
    $playTimeQuick = $playerCardJSON["quick-pt"];

    echo "
      <div id = 'playerCard'>
        <table>
          <tr>
            <td id = 'pcAvatar'>
              <img id ='avatar' src = '". $avatar . "'/> &nbsp;
            </td>
            <td id = 'pcTierSymbol'>
              <img src = '". $tier . "' /> &nbsp;
            </td>
            <td>
              Competitve &nbsp;
            </td>
            <td>
              Quick Play &nbsp;
            </td>
          </tr>
          <tr>
            <td id = 'pcPname'>
              " . $name . " &nbsp;
            </td>
            <td id = 'pcSR'>
              ". $rank . " &nbsp;
            </td>
            <td id = 'pcCompRecord'>
              ". $compWins . "-".$compLoss." &nbsp;
            </td>
            <td id = 'pcWins'>
              ".$quickWins." &nbsp;
            </td>
          </tr>
          <tr>
            <td id = 'pcLevel'>
              ".$level." &nbsp;
            </td>
            <td id = 'pcSeasonHigh'>

            </td>
            <td id = 'pcPlayTimeComp'>
              ".$playTimeComp." &nbsp;
            </td>
            <td id = 'pcPlayTimeQp'>
              ".$playTimeQuick." &nbsp;
            </td>
          </tr>
        </table>
      </div>
    ";
  }



?>
