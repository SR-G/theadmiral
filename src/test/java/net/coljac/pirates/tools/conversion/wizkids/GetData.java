package net.coljac.pirates.tools.conversion.wizkids;

import junit.framework.TestCase;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Feb 28, 2006
 */
public class GetData extends TestCase {

    // static Map<String, String> sets = new HashMap<String, String>();
    // static Map<String, String> factions = new HashMap<String, String>();
    //
    // static {
    // sets.put("PSM", "42");
    // sets.put("CC", "51");
    // sets.put("PR", "55");
    // sets.put("BC", "60");
    // sets.put("SCS", "72");
    //
    // factions.put("American", "135");
    // factions.put("French", "136");
    // factions.put("Spanish", "124");
    // factions.put("English", "121");
    // factions.put("Pirates", "122");
    // factions.put("Barbary Coarsairs", "150");
    // factions.put("Jade Rebellion", "151");
    // factions.put("Cursed", "182");
    // }
    //
    // String base = "http://www.wizkidsgames.com/pirates/";
    // String cardBase = base + "carddetail.asp?unitid=";
    //
    // public String cleanGuns(final String cannons) {
    // String result = cannons;
    // result = result.replaceAll(" *\\+ *", ",");
    // result = result.replaceAll(" +", ",");
    // result = result.replaceAll("2,2", "2S,2S");
    // result = result.replaceAll("3$", "3S");
    // return result;
    // }
    //
    // private String cleanMove(final String movement) {
    // String result = movement;
    // result = result.replaceAll(" *\\+ *", ",");
    // result = result.replaceAll(" +", ",");
    // result = result.replaceAll("1L", "L");
    // result = result.replaceAll("1S", "S");
    // result = result.replaceAll("1", "");
    // return result;
    // }
    //
    // private void file(final String name, final String text) {
    // try {
    // final PrintStream ps = new PrintStream(new FileOutputStream(name));
    // ps.print(text);
    // ps.flush();
    // ps.close();
    // } catch (final Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // private String fixRules(String rules) {
    // rules = rules.replaceAll("<IMG SRC=/images/icons/red.gif>", "L");
    // rules = rules.replaceAll("<<insert red square>>", "L");
    // rules = rules.replaceAll("<<insert white S>>", "S");
    // rules = rules.replaceAll("<<insert white die with a 6 showing>>", "6");
    // rules = rules.replaceAll("''", "'");
    // rules = rules.replaceAll("hip\\?s", "hip's");
    // rules = rules.replaceAll("hip\\?s", "hip's");
    // return rules;
    // }
    //
    // public void getCrew(final String set) throws Exception {
    // final PrintWriter pw = new PrintWriter(new FileWriter("crew_" + set + ".txt"));
    //
    // final WebConversation wc = new WebConversation();
    // final WebRequest req = new GetMethodWebRequest(base + "cardgallery.asp");
    // WebResponse resp = wc.getResponse(req);
    // final WebForm form = resp.getForms()[0];
    // // form.setParameter("ck_faction", "121");
    //
    // form.setParameter("ck_set", sets.get(set));
    // form.setParameter("ck_type", "792"); // crew
    //
    // int crew = 0;
    //
    // resp = form.submit();
    // crew += processCrews(resp, pw);
    //
    // for (int i = 2; i < 100; i++) {
    // final WebLink link = resp.getLinkWith("" + i);
    // if (link != null) {
    // crew += processCrews(link.click(), pw);
    // pw.flush();
    // } else {
    // break;
    // }
    // }
    // pw.close();
    //
    // System.out.println("Wrote " + crew + " crew.");
    // }
    //
    // private Crew getCrewCard(final String id) {
    // final String url = cardBase + id;
    // final String cacheFile = "./data/cache/" + id + "_" + url.hashCode();
    // String response = FileHelper.getFileContentsAsString(cacheFile);
    // if (response == null) {
    // response = WebTools.getURLAsString(url);
    // file(cacheFile, response);
    // }
    // final Crew crew = new Crew();
    // crew.setName(getTableValue("Name", response));
    // crew.setPoints(Integer.parseInt(getTableValue("Point Value", response)));
    // final String number = getTableValue("Collector's Number", response);
    // // if(number.indexOf("-")>0) {
    // // int base = -1;
    // // if(number.startsWith("PS")) {
    // // base = 0;
    // // }
    // // if(number.startsWith("ES")) {
    // // base = 40;
    // // }
    // // if(number.startsWith("SS")) {
    // // base = 68;
    // // }
    // // String remainder = number.substring(number.indexOf("-")+1);
    // // if(base>=0) {
    // // number = "" + (Integer.parseInt(remainder) + base);
    // // }
    // // }
    //
    // crew.setNumber(number);
    // crew.setRarity(getTableValue("Rarity", response));
    // crew.setRank(getTableValue("Rank", response));
    // crew.setLink(getTableValue("Link", response));
    //
    // final String marker = "<td><strong>";
    //
    // if (response.indexOf(marker) > -1) {
    // final int start = response.indexOf(marker);
    // final int end = response.indexOf("</td>", start);
    //
    // String cell = response.substring(start + 12, end - 4);
    // cell = cell.trim();
    // cell = cell.replaceAll("<P>", "<p>");
    // cell = cell.replaceAll("</strong>", "");
    // cell = cell.replaceAll("^ *<p>", "");
    // cell = cell.trim();
    // cell = cell.replaceAll("^<p>", "");
    // final String[] parts = cell.trim().split("<p>");
    // if (parts.length == 1) {
    // crew.setRules("");
    // crew.setFlavor(parts[0].trim());
    // } else {
    // crew.setRules(fixRules(parts[0].trim()));
    // crew.setFlavor(parts[1].trim());
    // }
    // }
    //
    // if (response.indexOf("src=\"images/Release") > -1) {
    // final int start = response.indexOf("src=\"images/Release");
    // final int end = response.indexOf("width", start);
    // String set = response.substring(start, end);
    // set = set.replaceAll(".*alt=\"(.*)\"", "$1");
    // crew.setExpansion(set.trim());
    // }
    //
    // if (response.indexOf("src=\"images/faction") > -1) {
    // final int start = response.indexOf("src=\"images/faction");
    // final int end = response.indexOf("width", start);
    // String faction = response.substring(start, end);
    // faction = faction.replaceAll(".*alt=\"(.*)\"", "$1");
    // crew.setFaction(faction.trim());
    // }
    // return crew;
    // }
    //
    // public Event getEvent(final String id) {
    // final String url = cardBase + id;
    // final String cacheFile = "./data/cache/" + id + "_" + url.hashCode();
    // String response = FileHelper.getFileContentsAsString(cacheFile);
    // if (response == null) {
    // response = WebTools.getURLAsString(url);
    // file(cacheFile, response);
    // }
    // final Event event = new Event();
    // event.setName(getTableValue("Name", response));
    // event.setPoints(Integer.parseInt(getTableValue("Point Value", response)));
    // final String colNumber = getTableValue("Collector's Number", response);
    // String number = colNumber;
    // if (number.indexOf("-") > 0) {
    // int base = -1;
    // if (number.startsWith("PS")) {
    // base = 0;
    // }
    // if (number.startsWith("ES")) {
    // base = 40;
    // }
    // if (number.startsWith("SS")) {
    // base = 68;
    // }
    // final String remainder = number.substring(number.indexOf("-") + 1);
    // if (base >= 0) {
    // number = "" + (Integer.parseInt(remainder) + base);
    // }
    // }
    //
    // event.setNumber(number);
    // event.setExtra(colNumber);
    // event.setRarity(getTableValue("Rarity", response));
    //
    // final String marker = "<td><strong>";
    //
    // if (response.indexOf(marker) > -1) {
    // final int start = response.indexOf(marker);
    // final int end = response.indexOf("</td>", start);
    //
    // String cell = response.substring(start + 12, end - 4);
    // cell = cell.trim();
    // cell = cell.replaceAll("<P>", "<p>");
    // cell = cell.replaceAll("</strong>", "");
    // cell = cell.replaceAll("^ *<p>", "");
    // cell = cell.trim();
    // cell = cell.replaceAll("^<p>", "");
    // final String[] parts = cell.trim().split("<p>");
    // if (parts.length == 1) {
    // event.setFlavor("");
    // event.setRules(parts[0].trim());
    // } else {
    // event.setRules(fixRules(parts[0].trim()));
    // event.setFlavor(parts[1].trim());
    // }
    // }
    //
    // if (response.indexOf("src=\"images/Release") > -1) {
    // final int start = response.indexOf("src=\"images/Release");
    // final int end = response.indexOf("width", start);
    // String set = response.substring(start, end);
    // set = set.replaceAll(".*alt=\"(.*)\"", "$1");
    // event.setExpansion(set.trim());
    // }
    //
    // if (response.indexOf("src=\"images/faction") > -1) {
    // final int start = response.indexOf("src=\"images/faction");
    // final int end = response.indexOf("width", start);
    // String faction = response.substring(start, end);
    // faction = faction.replaceAll(".*alt=\"(.*)\"", "$1");
    // event.setFaction(faction.trim());
    // }
    // return event;
    // }
    //
    // public void getEvents(final String set) throws Exception {
    // final PrintWriter pw = new PrintWriter(new FileWriter("events_all.txt"));
    // int evts = 0;
    //
    // final WebConversation wc = new WebConversation();
    // final WebRequest req = new GetMethodWebRequest("http://www.wizkidsgames.com/pirates/cardgallery.asp?f=&s=&t=790,%20793,%20825&y=&v=&p=&k=&sort=0&pp=1");
    // final WebResponse resp = wc.getResponse(req);
    //
    // evts += processEvents(resp, pw);
    //
    // for (int i = 2; i < 100; i++) {
    // final WebLink link = resp.getLinkWith("" + i);
    // if (link != null) {
    // evts += processEvents(link.click(), pw);
    // pw.flush();
    // } else {
    // break;
    // }
    // }
    // pw.flush();
    // pw.close();
    //
    // System.out.println("Wrote " + evts + " events.");
    // }
    //
    // private Fort getFort(final String id) {
    // final String url = cardBase + id;
    // final String cacheFile = "./data/cache/" + id + "_" + url.hashCode();
    // String response = FileHelper.getFileContentsAsString(cacheFile);
    // if (response == null) {
    // response = WebTools.getURLAsString(url);
    // file(cacheFile, response);
    // }
    // final Fort fort = new Fort();
    // fort.setName(getTableValue("Name", response));
    // fort.setPoints(Integer.parseInt(getTableValue("Point Value", response)));
    // final String colNumber = getTableValue("Collector's Number", response);
    // String number = colNumber;
    // if (number.indexOf("-") > 0) {
    // int base = -1;
    // if (number.startsWith("PS")) {
    // base = 0;
    // }
    // if (number.startsWith("ES")) {
    // base = 40;
    // }
    // if (number.startsWith("SS")) {
    // base = 68;
    // }
    // final String remainder = number.substring(number.indexOf("-") + 1);
    // if (base >= 0) {
    // number = "" + (Integer.parseInt(remainder) + base);
    // }
    // }
    //
    // final String goldCostString = getTableValue("Gold Cost", response);
    // if (goldCostString != null) {
    // final int cost = Integer.parseInt(goldCostString);
    // fort.setGoldCost(cost);
    // }
    //
    // fort.setNumber(number);
    // fort.setExtra(colNumber);
    // fort.setRarity(getTableValue("Rarity", response));
    //
    // String guns = "";
    // for (int i = 1; i < 7; i++) {
    // String gun = getTableValue("Mast " + i + " Cannon", response);
    // if (gun != null) {
    // guns += "," + gun;
    // } else {
    // gun = getTableValue("Gun " + i, response);
    // if (gun != null) {
    // guns += "," + gun;
    // } else {
    // break;
    // }
    // }
    // }
    // if (guns.startsWith(",")) {
    // guns = guns.substring(1);
    // }
    //
    // fort.setCannons(cleanGuns(guns));
    //
    // String masts = getTableValue("Cannons", response);
    // if (masts == null) {
    // masts = getTableValue(" Cannons", response);
    // }
    // fort.setMasts(Integer.parseInt(masts));
    //
    // // <td><strong>Broadsides Attack</strong>
    // // <p>Not every fort can live up to the name "La Magnifique," but with Capitaine de
    // // St. Croix in command, this fort is just that?nigh invincible when delivering a full cannon barrage.</p></td>
    //
    // final String marker = "<td><strong>";
    //
    // if (response.indexOf(marker) > -1) {
    // final int start = response.indexOf(marker);
    // final int end = response.indexOf("</td>", start);
    //
    // String cell = response.substring(start + 12, end - 4);
    // cell = cell.trim();
    // cell = cell.replaceAll("<P>", "<p>");
    // cell = cell.replaceAll("</strong>", "");
    // cell = cell.replaceAll("^ *<p>", "");
    // cell = cell.trim();
    // cell = cell.replaceAll("^<p>", "");
    // final String[] parts = cell.trim().split("<p>");
    // if (parts.length == 1) {
    // fort.setRules("No ability.");
    // fort.setFlavor(parts[0].trim());
    // } else {
    // fort.setRules(fixRules(parts[0].trim()));
    // fort.setFlavor(parts[1].trim());
    // }
    // }
    //
    // if (response.indexOf("src=\"images/Release") > -1) {
    // final int start = response.indexOf("src=\"images/Release");
    // final int end = response.indexOf("width", start);
    // String set = response.substring(start, end);
    // set = set.replaceAll(".*alt=\"(.*)\"", "$1");
    // fort.setExpansion(set.trim());
    // }
    //
    // if (response.indexOf("src=\"images/faction") > -1) {
    // final int start = response.indexOf("src=\"images/faction");
    // final int end = response.indexOf("width", start);
    // String faction = response.substring(start, end);
    // faction = faction.replaceAll(".*alt=\"(.*)\"", "$1");
    // fort.setFaction(faction.trim());
    // }
    // return fort;
    // }
    //
    // public void getForts(final String set) throws Exception {
    // final PrintWriter pw = new PrintWriter(new FileWriter("forts_all.txt"));
    // int forts = 0;
    //
    // final WebConversation wc = new WebConversation();
    // final WebRequest req = new GetMethodWebRequest("http://www.wizkidsgames.com/pirates/cardgallery.asp?f=&s=&t=790,%20793,%20825&y=&v=&p=&k=&sort=0&pp=1");
    // final WebResponse resp = wc.getResponse(req);
    //
    // forts += processForts(resp, pw);
    //
    // for (int i = 2; i < 100; i++) {
    // final WebLink link = resp.getLinkWith("" + i);
    // if (link != null) {
    // forts += processForts(link.click(), pw);
    // pw.flush();
    // } else {
    // break;
    // }
    // }
    // pw.flush();
    // pw.close();
    //
    // System.out.println("Wrote " + forts + " forts.");
    // }
    //
    // private Ship getShip(final String id) {
    // final String url = cardBase + id;
    // final String cacheFile = "./data/cache/" + id + "_" + url.hashCode();
    // String response = FileHelper.getFileContentsAsString(cacheFile);
    // if (response == null) {
    // response = WebTools.getURLAsString(url);
    // file(cacheFile, response);
    // }
    // final Ship ship = new Ship();
    // ship.setName(getTableValue("Name", response));
    // ship.setPoints(Integer.parseInt(getTableValue("Point Value", response)));
    // final String colNumber = getTableValue("Collector's Number", response);
    // String number = colNumber;
    // if (number.indexOf("-") > 0) {
    // int base = -1;
    // if (number.startsWith("PS")) {
    // base = 0;
    // }
    // if (number.startsWith("ES")) {
    // base = 40;
    // }
    // if (number.startsWith("SS")) {
    // base = 68;
    // }
    // final String remainder = number.substring(number.indexOf("-") + 1);
    // if (base >= 0) {
    // number = "" + (Integer.parseInt(remainder) + base);
    // }
    // }
    //
    // ship.setNumber(number);
    // ship.setExtra(colNumber);
    // ship.setRarity(getTableValue("Rarity", response));
    // String cargoSpace = getTableValue("Cargo Space", response);
    // if (cargoSpace == null) {
    // cargoSpace = getTableValue("Capacity", response);
    // }
    // ship.setCargo(Integer.parseInt(cargoSpace));
    // String guns = "";
    // for (int i = 1; i < 7; i++) {
    // final String gun = getTableValue("Mast " + i + " Cannon", response);
    // if (gun != null) {
    // guns += "," + gun;
    // } else {
    // break;
    // }
    // }
    // if (guns.startsWith(",")) {
    // guns = guns.substring(1);
    // }
    //
    // ship.setCannons(cleanGuns(guns));
    // String movement = getTableValue("Movement", response);
    // if (movement == null) {
    // movement = getTableValue("Move", response);
    // }
    // ship.setMove(cleanMove(movement));
    // String masts = getTableValue("Number of Masts", response);
    // if (masts == null) {
    // masts = getTableValue("Mast", response);
    // }
    // ship.setMasts(Integer.parseInt(masts));
    //
    // // <td><strong>Broadsides Attack</strong>
    // // <p>Not every ship can live up to the name "La Magnifique," but with Capitaine de
    // // St. Croix in command, this ship is just that?nigh invincible when delivering a full cannon barrage.</p></td>
    //
    // final String marker = "<td><strong>";
    //
    // if (response.indexOf(marker) > -1) {
    // final int start = response.indexOf(marker);
    // final int end = response.indexOf("</td>", start);
    //
    // String cell = response.substring(start + 12, end - 4);
    // cell = cell.trim();
    // cell = cell.replaceAll("<P>", "<p>");
    // cell = cell.replaceAll("</strong>", "");
    // cell = cell.replaceAll("^ *<p>", "");
    // cell = cell.trim();
    // cell = cell.replaceAll("^<p>", "");
    // final String[] parts = cell.trim().split("<p>");
    // if (parts.length == 1) {
    // ship.setRules("No ability.");
    // ship.setFlavor(parts[0].trim());
    // } else {
    // ship.setRules(fixRules(parts[0].trim()));
    // ship.setFlavor(parts[1].trim());
    // }
    // }
    //
    // if (response.indexOf("src=\"images/Release") > -1) {
    // final int start = response.indexOf("src=\"images/Release");
    // final int end = response.indexOf("width", start);
    // String set = response.substring(start, end);
    // set = set.replaceAll(".*alt=\"(.*)\"", "$1");
    // ship.setExpansion(set.trim());
    // }
    //
    // if (response.indexOf("src=\"images/faction") > -1) {
    // final int start = response.indexOf("src=\"images/faction");
    // final int end = response.indexOf("width", start);
    // String faction = response.substring(start, end);
    // faction = faction.replaceAll(".*alt=\"(.*)\"", "$1");
    // ship.setFaction(faction.trim());
    // }
    // return ship;
    // }
    //
    // public void getShips(final String set) throws Exception {
    // final PrintWriter pw = new PrintWriter(new FileWriter("ships_" + set + ".txt"));
    //
    // final WebConversation wc = new WebConversation();
    // final WebRequest req = new GetMethodWebRequest(base + "cardgallery.asp");
    // WebResponse resp = wc.getResponse(req);
    // final WebForm form = resp.getForms()[0];
    // // form.setParameter("ck_faction", "121");
    //
    // // form.setParameter("ck_set", sets.get("PSM"));
    // // form.setParameter("ck_set", sets.get("CC"));
    // // form.setParameter("ck_set", sets.get("PR"));
    // // form.setParameter("ck_set", sets.get("BC"));
    // // form.setParameter("ck_set", sets.get("SCS"));
    // form.setParameter("ck_set", sets.get(set));
    //
    // form.setParameter("ck_type", "789");
    //
    // // form.setParameter("ck_11thru15", "1");
    //
    // int ships = 0;
    //
    // resp = form.submit();
    // ships += processShips(resp, pw);
    //
    // for (int i = 2; i < 100; i++) {
    // final WebLink link = resp.getLinkWith("" + i);
    // if (link != null) {
    // ships += processShips(link.click(), pw);
    // pw.flush();
    // } else {
    // break;
    // }
    // }
    // pw.close();
    //
    // System.out.println("Wrote " + ships + " ships.");
    // }
    //
    // private String getTableValue(final String cell, final String text) {
    // final int start = text.indexOf("<span class=\"style6\">&nbsp;" + cell);
    // if (start == -1) {
    // return null;
    // }
    // final int spanEnd = text.indexOf("</span>", start);
    // final int end = text.indexOf("&nbsp;", spanEnd) + 6;
    // final String value = text.substring(end, text.indexOf("</span>", end));
    // return value.trim();
    // }
    //
    // private int processCrews(final WebResponse resp, final PrintWriter pw) throws SAXException {
    // int crews = 0;
    // final WebLink[] links = resp.getLinks();
    // for (int i = 0; i < links.length; i++) {
    // final WebLink link = links[i];
    // if (link.getText().indexOf("Crew") > 0) {
    // final int start = link.getURLString().indexOf("unitid=") + 7;
    // final int end = link.getURLString().indexOf("'", start);
    // final String id = link.getURLString().substring(start, end);
    // final Crew crew = getCrewCard(id);
    // MakeDB.db.getCrew().add(crew);
    // MakeDB.db.getCards().add(crew);
    //
    // if (pw != null) {
    // pw.println(crew.toCSV());
    // }
    // crews++;
    // }
    // }
    // // HibernateUtil.commit();
    // return crews;
    // }
    //
    // public int processEvents(final WebResponse resp, final PrintWriter pw) throws SAXException {
    // int evts = 0;
    // final WebLink[] links = resp.getLinks();
    // for (int i = 0; i < links.length; i++) {
    // final WebLink link = links[i];
    // if (!(link.getText().indexOf("(Event)") > 0)) {
    // continue;
    // }
    // if (link.getText().indexOf("Event") > 0) {
    // final int start = link.getURLString().indexOf("unitid=") + 7;
    // final int end = link.getURLString().indexOf("'", start);
    // final String id = link.getURLString().substring(start, end);
    // final Event event = getEvent(id);
    // MakeDB.db.getEvents().add(event);
    // MakeDB.db.getCards().add(event);
    //
    // if (pw != null) {
    // pw.println(event.toCSV());
    // pw.flush();
    // }
    // evts++;
    // }
    // }
    // // HibernateUtil.commit();
    // return evts;
    // }
    //
    // public int processForts(final WebResponse resp, final PrintWriter pw) throws SAXException {
    // int evts = 0;
    // final WebLink[] links = resp.getLinks();
    // for (int i = 0; i < links.length; i++) {
    // final WebLink link = links[i];
    // if (!(link.getText().indexOf("(Fort)") > 0)) {
    // continue;
    // }
    // if (link.getText().indexOf("Firepot") < 0) {
    // final int start = link.getURLString().indexOf("unitid=") + 7;
    // final int end = link.getURLString().indexOf("'", start);
    // final String id = link.getURLString().substring(start, end);
    // final Fort fort = getFort(id);
    // MakeDB.db.getShips().add(fort);
    // MakeDB.db.getCards().add(fort);
    //
    // if (pw != null) {
    // pw.println(fort.toCSV());
    // pw.flush();
    // }
    // evts++;
    // }
    // }
    // // HibernateUtil.commit();
    // return evts;
    // }
    //
    // private int processShips(final WebResponse resp, final PrintWriter pw) throws SAXException {
    // int ships = 0;
    // final WebLink[] links = resp.getLinks();
    // for (int i = 0; i < links.length; i++) {
    // final WebLink link = links[i];
    // if (link.getText().indexOf("Ship") > 0) {
    // final int start = link.getURLString().indexOf("unitid=") + 7;
    // final int end = link.getURLString().indexOf("'", start);
    // final String id = link.getURLString().substring(start, end);
    // final Ship ship = getShip(id);
    // MakeDB.db.getShips().add(ship);
    // MakeDB.db.getCards().add(ship);
    //
    // if (pw != null) {
    // pw.println(ship.toCSV());
    // }
    // ships++;
    // }
    // }
    // // HibernateUtil.commit();
    // return ships;
    // }

}
