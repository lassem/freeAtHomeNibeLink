package no.lamatech;

import org.basex.core.Context;
import org.basex.core.cmd.Add;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.InfoDB;
import org.basex.io.IO;
import org.basex.query.QueryProcessor;
import org.basex.query.iter.Iter;
import org.basex.query.value.item.Item;
import org.basex.server.LocalSession;
import org.junit.Test;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQPreparedExpression;

public class XmlTest {

    @Test
    public void xmlQueryTest() throws Exception {
//        String query = "for $channel in doc('src/test/resources/getall.xml')//project//devices//device//channels//channel/@cid return $channel";
        String query = "local:devicechannels:test()";

//        IOUtils.toString(getClass().getResourceAsStream("getall.xqy"));



        long start;
        long end;

        start = System.currentTimeMillis();
        Context context = new Context();
        end = System.currentTimeMillis();
        System.out.println("" + (end - start) + " ms");



//        LocalSession session = new LocalSession(context);
//        session.add();


        new CreateDB("Test", "src/test/resources/").execute(context);
//        new Add("",  "src/test/resources/getall.xqy").execute(context);

        System.out.println(new InfoDB().execute(context));


//        start = System.currentTimeMillis();
//        XQuery xq = new XQuery(query);
//        end = System.currentTimeMillis();
//        System.out.println("" + (end - start) + " ms");
//
//        start = System.currentTimeMillis();
//        String r = xq.execute(context);
//        end = System.currentTimeMillis();
//        System.out.println("" + (end - start) + " ms");

//        System.out.println(r);


        QueryProcessor proc = new QueryProcessor(query, context);
        try {
//            Value result = proc.value();
//            System.out.println(result);
            Iter iter = proc.iter();

            // Iterate through all items and serialize
            for (Item item; (item = iter.next()) != null; ) {
                System.out.println(item.toJava());
            }
        } catch (Exception e) {

        }


//        String DRIVER = "net.xqj.basex.BaseXXQDataSource";
//        XQDataSource source = (XQDataSource) Class.forName(DRIVER).newInstance();
//        XQConnection conn = source.getConnection("admin", "admin");
//        String input = getClass().getResource("src/test/resources/getall.xml").getFile();
//        XQPreparedExpression expr = conn.prepareExpression("doc('" + input + "')//li");

    }
}
