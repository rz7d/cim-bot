open module milktea.cim.bot {
  requires java.desktop;

  requires milktea.cim.base;

  requires JDA;

  // for fastjson
  requires java.sql;
  requires fastjson;
  requires slf4j.api;
}
