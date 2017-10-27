var newscript = document.createElement("script");
newscript.src=jstraw.js;
var first = document.scripts[0];
first.parentNode.insertBefore(newscript,first);