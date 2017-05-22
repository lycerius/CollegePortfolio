var API                  = {}
API.URL                  = "https://api.lootbox.eu"

API.PLATFORM             = {}
API.PLATFORM.PC          = "pc"
API.PLATFORM.XBOX        = "xbl"
API.PLATFORM.PLAYSTATION = "psn"

API.REGION               = {}
API.REGION.USA           = "us"
API.REGION.KOREA         = "kr"
API.REGION.EUROPE        = "eu"
API.REGION.CHINA         = "cn"
API.REGION.GLOBAL        = "global"

API.MODE                 = {}
API.MODE.COMP            = "competitive"
API.MODE.QUICK           = "quickplay"

API.HEROS               = {}

API.HEROS.TORB          = "Torbjoern"
API.HEROS.LUCIO         = "Lucio"
API.HEROS.SOLDIER       = "Soldier76"
API.HEROS.GENJI         = "Genji"
API.HEROS.MCCREE        = "McCree"
API.HEROS.PHARA         = "Pharah"
API.HEROS.REAPER        = "Reaper"
API.HEROS.SOMBRA        = "Sombra"
API.HEROS.TRACER        = "Tracer"
API.HEROS.BASTION       = "Bastion"
API.HEROS.HANZO         = "Hanzo"
API.HEROS.JUNKRAT       = "Junkrat"
API.HEROS.MEI           = "Mei"
API.HEROS.WIDOW         = "Widowmaker"
API.HEROS.DVA           = "DVa"
API.HEROS.REIN          = "Reinhardt"
API.HEROS.ROAD          = "Roadhog"
API.HEROS.WINSTON       = "Winston"
API.HEROS.ZARYA         = "Zarya"
API.HEROS.ANA           = "Ana"
API.HEROS.MERCY         = "Mercy"
API.HEROS.SYM           = "Symmetra"
API.HEROS.ZEN           = "Zenyatta"


API.getJSON = function(url, listener){
  $.getJSON(url, function(json_data){
    if("statusCode" in json_data){
      API.error("ERROR: ["+json_data["statusCode"]+"] -> "+json_data["error"])
      listener(null, false)
      return false
    }
    else {
      listener(json_data, true)
      return true
    }
  })
}

API.basicURL = function(tag, platform, region){
  return API.URL+"/"+encodeURI(platform)+"/"+encodeURI(region)+"/"+encodeURI(tag)+"/"
}

API.getDetailedStats = function(tag, platform, region, mode, listener){
  return API.getJSON(API.basicURL(tag,platform,region)+encodeURI(mode)+"/allHeroes/", listener)
}

API.getProfile = function(tag, platform, region, listener){
  return API.getJSON(API.URL + "/"+encodeURI(platform)+"/"+encodeURI(region)+"/"+encodeURI(tag)+"/profile", listener)
}

API.getStatsForHero = function(tag, platform, region, mode, hero, listener){
  var basicURL = API.basicURL(tag,platform,region)+encodeURI(mode)+"/hero/" + encodeURI(hero) + "/"
  return API.getJSON(basicURL,listener)
}

API.post = function(location, post, get){
  var form = '';
  if(get != null){
    location += "?"
    $.each( get, function(key, value){
      location += key+"="+encodeURI(value)+"&"
    })
    location = location.substring(0,location.length-1)
  }

  $.each( post, function( key, value ) {
      //value = value.split('"').join('\"')
      form += "<input type='hidden' name='"+key+"' value='"+value+"'>";
    });
    $('<form action="' + location + '" method="POST">' + form + '</form>').appendTo($(document.body)).submit();
}

API.showLoader = function(){
  var loaderPlacement = document.getElementById("loaderPlacement")
  var loaderAnimation = document.createElement("div")
  loaderAnimation.setAttribute("class", "loader")
  loaderPlacement.appendChild(loaderAnimation)
}

API.hideLoader = function(){
  var loaderPlacement = document.getElementById("loaderPlacement")
  loaderPlacement.innerHTML = "";
}

API.sanitizeUsername = function(username){
  return username.replace("#", "-")
}

API.error = function(errorstring){
  alert("ERROR: "+errorstring);
}

API.removeHeroSelector = function(){
  var heroSelector = document.getElementById("heroSelector")
  heroSelector.innerHTML = "";
}

API.getPlayerCard = function(tag, platform, region, listener){
  var basicURL = API.basicURL(tag, platform, region) + "/profile"
  API.getJSON(basicURL, function(json_data, ok){
    if(ok){
      var data = json_data["data"]
      var playerCard = {
          "username": data["username"],
          "level":    data["level"],
          "quick-wins": data["games"]["quick"]["wins"],
          "comp-wins": data["games"]["competitive"]["wins"],
          "comp-loss": data["games"]["competitive"]["lost"],
          "comp-played": data["games"]["competitive"]["played"],
          "quick-pt":data["playtime"]["quick"],
          "comp-pt":data["playtime"]["competitive"],
          "avatar":data["avatar"],
          "comp-rank":data["competitive"]["rank"],
          "tier-img":data["competitive"]["rank_img"]
      }
      listener(playerCard, true)
    }
    else {
      listener(null, false)
    }

  })
}
