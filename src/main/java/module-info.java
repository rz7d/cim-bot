open module milktea.cim.bot {
  requires java.desktop;
  requires java.logging;

  requires milktea.cim.base;

  requires JDA;
  requires jul.to.slf4j;

  // for fastjson
  requires java.sql;
  requires fastjson;
}