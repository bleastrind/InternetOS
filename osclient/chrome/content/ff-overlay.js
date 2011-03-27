osclient.onFirefoxLoad = function(event) {
  document.getElementById("contentAreaContextMenu")
          .addEventListener("popupshowing", function (e){ osclient.showFirefoxContextMenu(e); }, false);
};

osclient.showFirefoxContextMenu = function(event) {
  // show or hide the menuitem based on what the context menu is on
  document.getElementById("context-osclient").hidden = gContextMenu.onImage;
};

window.addEventListener("load", function () { osclient.onFirefoxLoad(); }, false);
