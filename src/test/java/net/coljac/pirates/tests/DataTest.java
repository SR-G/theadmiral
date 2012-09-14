package net.coljac.pirates.tests;

import java.util.List;

import junit.framework.TestCase;
import net.coljac.pirates.Crew;
import net.coljac.pirates.Fleet;
import net.coljac.pirates.Ship;
import net.coljac.pirates.tools.conversion.wizkids.HibernateUtil;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Feb 28, 2006
 */
public class DataTest extends TestCase {

    public void testData() throws Exception {

        HibernateUtil.currentSession();
        final Ship ship = new Ship("SHIP");
        ship.setCannons("3S,3L");
        ship.setCargo(2);
        ship.setMasts(3);
        ship.setPoints(4);
        HibernateUtil.save(ship);
        HibernateUtil.commit();

        List l = HibernateUtil.createQuery("from Ship as ship").list();
        assertEquals(1, l.size());

        final Crew crew = new Crew();
        crew.setName("crew");
        crew.setExtra("aa");
        crew.setOwned(1);
        HibernateUtil.save(crew);
        HibernateUtil.commit();

        l = HibernateUtil.createQuery("from Crew as crew").list();
        assertEquals(1, l.size());

        l = HibernateUtil.createQuery("from Card as card").list();
        assertEquals(2, l.size());

        final Fleet fleet = new Fleet();
        fleet.setName("BOB");
        fleet.addShip(ship);
        fleet.addCrew(crew);
        fleet.addCrewToShip(ship, crew);
        HibernateUtil.save(fleet);
        HibernateUtil.commit();

        final Ship ship2 = new Ship();
        ship2.setName("SHIP 2");
        ship2.setPoints(2);
        HibernateUtil.save(ship2);
        fleet.addShip(ship2);
        HibernateUtil.currentSession().update(fleet);
        HibernateUtil.commit();

        l = HibernateUtil.createQuery("from Fleet as fleet").list();
        assertEquals(1, l.size());
        final Fleet f2 = (Fleet) l.get(0);
        assertEquals(f2.getShips().get(0).getName(), "SHIP");

    }

}
