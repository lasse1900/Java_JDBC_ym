
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.ClassRef;
import java.lang.reflect.Field;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import sanakirja.UseanKaannoksenSanakirja;

@Points("131")
public class UseanKaannoksenSanakirjaTest {

    String klassName = "sanakirja.OmaUseanKaannoksenSanakirja";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 3, "oikeastaan muuta kuin käännökset tallentavan oliomuuttujan");
    }

    @Test
    public void tyhjaKonstruktori() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + s(klassName) + " julkinen konstruktori: "
                + "public " + s(klassName) + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new OmaUseanKaannoksenSanakirja();";
        ctor.withNiceError(v).invoke();
    }

    public Object luo() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    @Test
    public void toteuttaaRajapinnan() {
        Class clazz = ReflectionUtils.findClass(klassName);

        boolean toteuttaaRajapinnan = false;
        Class kali = UseanKaannoksenSanakirja.class;
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(kali)) {
                toteuttaaRajapinnan = true;
            }
        }

        if (!toteuttaaRajapinnan) {
            fail("Toteuttaahan luokka OmaUseanKaannoksenSanakirja rajapinnan UseanKaannoksenSanakirja?");
        }
    }

    /*
     * 
     */
    @Test
    public void lisaaMetodi() throws Throwable {
        String metodi = "lisaa";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(String sana, String kaannos) ",
                klass.method(olio, metodi)
                .returningVoid().taking(String.class, String.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");";

        klass.method(olio, metodi)
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("apina", "monkey");
    }

    @Test
    public void kaannaMetodi() throws Throwable {
        String metodi = "kaanna";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public Set<String> " + metodi + "(String sana) ",
                klass.method(olio, metodi)
                .returning(Set.class)
                .taking(String.class)
                .isPublic());

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).invoke("apina", "monkey");

        String v = "\nVirheen aiheuttanut koodi \n"
                + "UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");\n"
                + "s.kaanna(\"apina\");\n";


        Set vast = new HashSet();
        vast.add("monkey");

        assertEquals(v, vast, klass.method(olio, metodi)
                .returning(Set.class).taking(String.class).withNiceError(v).invoke("apina"));
    }

    @Test
    public void kaannaMetodiEiSanaa() throws Throwable {
        String metodi = "kaanna";

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi\n"
                + "UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.kaanna(\"apina\");\n";

        assertEquals(v, null, klass.method(olio, metodi)
                .returning(Set.class).taking(String.class).withNiceError(v).invoke("apina"));
    }

    @Test
    public void kaannaMetodiKaksiKaannosta() throws Throwable {
        String metodi = "kaanna";

        Object olio = luo();

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).invoke("apina", "monkey");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).invoke("apina", "apfe");

        String v = "\nVirheen aiheuttanut koodi \n"
                + "UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");\n"
                + "s.lisaa(\"apina\",\"apfe\");\n"
                + "s.kaanna(\"apina\");\n";


        Set vast = new HashSet();
        vast.add("monkey");
        vast.add("apfe");

        assertEquals(v, vast, klass.method(olio, metodi)
                .returning(Set.class).taking(String.class).withNiceError(v).invoke("apina"));
    }

    @Test
    public void kaannaMetodiMontaSanaa() throws Throwable {
        String metodi = "kaanna";

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi \n"
                + "UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");\n"
                + "s.lisaa(\"apina\",\"apfe\");\n"
                + "s.lisaa(\"juusto\",\"cheese\");\n"
                + "s.lisaa(\"maito\",\"milk\");\n"
                + "s.kaanna(\"apina\");\n";

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("apina", "monkey");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("apina", "apfe");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("juusto", "cheese");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("maito", "milk");

        Set vast = new HashSet();
        vast.add("monkey");
        vast.add("apfe");

        assertEquals(v, vast, klass.method(olio, metodi)
                .returning(Set.class).taking(String.class).withNiceError(v).invoke("apina"));

        v = "\nVirheen aiheuttanut koodi \n"
                + "UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");\n"
                + "s.lisaa(\"apina\",\"apfe\");\n"
                + "s.lisaa(\"juusto\",\"cheese\");\n"
                + "s.lisaa(\"maito\",\"milk\");\n"
                + "s.kaanna(\"juusto\");\n";

        vast = new HashSet();
        vast.add("cheese");
        assertEquals(v, vast, klass.method(olio, metodi)
                .returning(Set.class).taking(String.class).withNiceError(v).invoke("juusto"));

        v = "\nVirheen aiheuttanut koodi \n"
                + "UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");\n"
                + "s.lisaa(\"apina\",\"apfe\");\n"
                + "s.lisaa(\"juusto\",\"cheese\");\n"
                + "s.lisaa(\"maito\",\"milk\");\n"
                + "s.kaanna(\"peruna\");\n";

        assertEquals(v, null, klass.method(olio, metodi)
                .returning(Set.class).taking(String.class).withNiceError(v).invoke("peruna"));
    }

    public void testaaYhdenKaannoksenLisays() {
        UseanKaannoksenSanakirja sanakirja = luoInstanssi();
        Set<String> kaannokset = new HashSet<String>();
        kaannokset.add("word");

        testaaKaannos(sanakirja, "sana", kaannokset);
    }

    @Test
    public void testaaMonenKaannoksenLisays() {
        UseanKaannoksenSanakirja sanakirja = luoInstanssi();
        Set<String> kaannokset = new HashSet<String>();
        kaannokset.add("word");

        testaaKaannos(sanakirja, "sana", kaannokset);

        kaannokset.add("ord");
        kaannokset.add("käännös1");
        kaannokset.add("käännös2");

        testaaKaannos(sanakirja, "sana", kaannokset);
    }

    @Test
    public void testaaMonenSananLisays() {
        UseanKaannoksenSanakirja sanakirja = luoInstanssi();
        Set<String> kaannokset = new HashSet<String>();
        kaannokset.add("word");
        kaannokset.add("ord");
        kaannokset.add("käännös1");
        kaannokset.add("käännös2");

        testaaKaannos(sanakirja, "sana", kaannokset);

        Set<String> kaannokset2 = new HashSet<String>();
        kaannokset2.add("jungle");
        kaannokset2.add("jungel");
        kaannokset2.add("käännös3");
        kaannokset2.add("käännös4");

        testaaKaannos(sanakirja, "viidakko", kaannokset2);
    }

    /*
     * 
     */
    @Test
    public void poistaMetodi() throws Throwable {
        String metodi = "poista";

        Object olio = luo();

        assertTrue("tee luokalle " + klassName + " metodi public String " + metodi + "(String sana) ",
                klass.method(olio, metodi)
                .returningVoid()
                .taking(String.class)
                .isPublic());
    }

    @Test
    public void poistaOlemassaoleva() throws Throwable {
        String metodi = "poista";

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi \n"
                + "UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");\n"
                + "s.poista(\"apina\");\n"
                + "s.kaanna(\"apina\");";

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).invoke("apina", "monkey");

        klass.method(olio, "poista")
                .returningVoid().taking(String.class).withNiceError(v).invoke("apina");

        assertEquals(v, null, klass.method(olio, "kaanna")
                .returning(Set.class).taking(String.class).withNiceError(v).invoke("apina"));
    }

    public void poistaJosUseita() throws Throwable {
        String metodi = "poista";

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi \n"
                + "UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");\n"
                + "s.lisaa(\"apina\",\"apfe\");\n"
                + "s.poista(\"apina\");\n"
                + "s.kaanna(\"apina\");";

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).invoke("apina", "monkey");

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).invoke("apina", "apfe");

        klass.method(olio, "posita")
                .returningVoid().taking(String.class).withNiceError(v).invoke("apina");

        assertEquals(v, null, klass.method(olio, "kaanna")
                .returning(Set.class).taking(String.class).withNiceError(v).invoke("apina"));
    }

    @Test
    public void montaSanaaKaantojaJaPoistoja() throws Throwable {
        String metodi = "kaanna";

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi \n"
                + "UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");\n"
                + "s.lisaa(\"apina\",\"apfe\");\n"
                + "s.lisaa(\"juusto\",\"cheese\");\n"
                + "s.poista(\"apina\");"
                + "s.lisaa(\"maito\",\"milk\");\n"
                + "s.kaanna(\"apina\");\n";

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("apina", "monkey");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("apina", "apfe");
        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("juusto", "cheese");
        klass.method(olio, "poista")
                .returningVoid().taking(String.class).withNiceError(v).invoke("apina");

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("maito", "milk");

        assertEquals(v, null, klass.method(olio, metodi)
                .returning(Set.class).taking(String.class).withNiceError(v).invoke("apina"));

        v = "\nVirheen aiheuttanut koodi \n"
                + "UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");\n"
                + "s.lisaa(\"apina\",\"apfe\");\n"
                + "s.lisaa(\"juusto\",\"cheese\");\n"
                + "s.poista(\"apina\");"
                + "s.lisaa(\"maito\",\"milk\");\n"
                + "s.kaanna(\"juusto\");\n";

        HashSet vast = new HashSet();
        vast.add("cheese");
        assertEquals(v, vast, klass.method(olio, metodi)
                .returning(Set.class).taking(String.class).withNiceError(v).invoke("juusto"));

        v = "\nVirheen aiheuttanut koodi \n"
                + "UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");\n"
                + "s.lisaa(\"apina\",\"apfe\");\n"
                + "s.lisaa(\"juusto\",\"cheese\");\n"
                + "s.poista(\"apina\");\n"
                + "s.lisaa(\"maito\",\"milk\");\n"
                + "s.lisaa(\"apina\",\"monkee\");\n"
                + "s.kaanna(\"apina\");\n";


        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("apina", "monkee");


        vast = new HashSet();
        vast.add("monkee");
        assertEquals(v, vast, klass.method(olio, metodi)
                .returning(Set.class).taking(String.class).withNiceError(v).invoke("apina"));
    }

    public void poistaOlematon() throws Throwable {
        String metodi = "poista";

        Object olio = luo();

        String v = "\nVirheen aiheuttanut koodi \n"
                + "UseanKaannoksenSanakirja s = new OmaUseanKaannoksenSanakirja();\n"
                + "s.lisaa(\"apina\",\"monkey\");\n"
                + "s.poista(\"kerma\");\n"
                + "s.kaanna(\"apina\");";

        klass.method(olio, "lisaa")
                .returningVoid().taking(String.class, String.class).invoke("apina", "monkey");

        klass.method(olio, "posita")
                .returningVoid().taking(String.class).withNiceError(v).invoke("kerma");


        Set vast = new HashSet();
        vast.add("monkey");
        vast.add("apfe");

        assertEquals(v, vast, klass.method(olio, metodi)
                .returning(Set.class).taking(String.class).withNiceError(v).invoke("apina"));
    }

    @Test
    public void testaaMonenSananLisaysJaPoisto() {
        UseanKaannoksenSanakirja sanakirja = luoInstanssi();
        Set<String> kaannokset = new HashSet<String>();
        kaannokset.add("word");
        kaannokset.add("ord");
        kaannokset.add("käännös1");
        kaannokset.add("käännös2");

        testaaKaannos(sanakirja, "sana", kaannokset);

        Set<String> kaannokset2 = new HashSet<String>();
        kaannokset2.add("jungle");
        kaannokset2.add("jungel");
        kaannokset2.add("käännös3");
        kaannokset2.add("käännös4");

        testaaKaannos(sanakirja, "viidakko", kaannokset2);

        testaaPoisto(sanakirja, "sana");

        testaaPoisto(sanakirja, "viidakko");
    }

    @Test
    public void testaaOlematonSana() {
        UseanKaannoksenSanakirja sanakirja = luoInstanssi();
        testaaOlematonSana(sanakirja, "olematonsana");

        Set<String> kaannokset2 = new HashSet<String>();
        kaannokset2.add("jungle");
        kaannokset2.add("jungel");
        kaannokset2.add("käännös3");
        kaannokset2.add("käännös4");

        testaaKaannos(sanakirja, "viidakko", kaannokset2);

        testaaPoisto(sanakirja, "sana");

        testaaOlematonSana(sanakirja, "olematonsana2");
    }

    private void testaaOlematonSana(UseanKaannoksenSanakirja sanakirja,
            String sana) {
        Set<String> kaannokset = sanakirja.kaanna(sana);
        Assert.assertTrue("Sanakirjasta kysyttiin sanaa \"" + sana + "\", "
                + "jota ei oltu lisätty sanakirjaan, mutta palautettu käännösten oli: "
                + kaannokset, kaannokset == null || kaannokset.isEmpty());
    }

    private void testaaKaannos(UseanKaannoksenSanakirja sanakirja,
            String sana, Set<String> kaannokset) {
        for (String kaannos : kaannokset) {
            sanakirja.lisaa(sana, kaannos);
        }

        Set<String> palautetut = sanakirja.kaanna(sana);
        if (palautetut == null) {
            Assert.fail("Sanakirjaan lisättiin sana \"" + sana + "\" "
                    + "käännöksillä: " + kaannokset + ", mutta kaanna()-metodi "
                    + "palauttaa sanalle null.");
            return;
        }

        Assert.assertTrue("Sanakirjaan lisättiin sana \"" + sana + "\" "
                + "käännöksillä: " + kaannokset + ", mutta "
                + "palautetuttujen käännösten lista oli: " + palautetut,
                palautetut.containsAll(kaannokset));
    }

    private void testaaPoisto(UseanKaannoksenSanakirja sanakirja,
            String sana) {
        sanakirja.poista(sana);
        Set<String> kaannokset = sanakirja.kaanna(sana);
        Assert.assertTrue("Sanakirjasta poistettiin sana \"" + sana + "\", "
                + "mutta palautettu käännösten lista ei ollut null tai tyhjä: "
                + kaannokset, kaannokset == null || kaannokset.isEmpty());
    }

    private UseanKaannoksenSanakirja luoInstanssi() {
        String luokanNimi = "sanakirja.OmaUseanKaannoksenSanakirja";
        ClassRef<?> luokka;
        try {
            luokka = Reflex.reflect(luokanNimi);
        } catch (Throwable t) {
            Assert.fail("Luokkaa " + luokanNimi + " ei ole olemassa. Tässä tehtävässä täytyy luoda kyseinen luokka.");
            return null;
        }
        if (!UseanKaannoksenSanakirja.class.isAssignableFrom(
                luokka.getReferencedClass())) {
            Assert.fail("Luokan " + luokanNimi + " täytyy "
                    + "toteuttaa rajapinta sanakirja.UseanKaannoksenSanakirja");
        }

        Object instanssi;
        try {
            instanssi = luokka.constructor().takingNoParams().invoke();
        } catch (Throwable t) {
            Assert.fail("Luokalla " + luokanNimi + " ei ole julkista parametritonta konstruktoria.");
            return null;
        }

        return (UseanKaannoksenSanakirja) instanssi;
    }

    private String s(String klassName) {
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }

    private void saniteettitarkastus(String klassName, int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista luokalta " + s(klassName) + " muuttuja " + kentta(field.toString(), s(klassName)), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta luokalta " + s(klassName) + " löytyi: " + kentta(field.toString(), klassName), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + s(klassName) + "-luokalle kuin " + m + ", poista ylimääräiset", var <= n);
        }
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "").replace("java.io.", "");
    }
}
