package pl.lukas.fetchTypes;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.lukas.fetchTypes.entity.*;

public class FetchTypeApp {

    public static void main(String[] args) {
        // tworzenie obiektu Configuration
        Configuration conf = new Configuration();
        // wczytanie pliku konfiguracyjnego
        conf.configure("hibernate.cfg.xml");
        // wczytanie adnotacje klasy
        conf.addAnnotatedClass(Company.class);
        conf.addAnnotatedClass(CompanyDetail.class);
        conf.addAnnotatedClass(Property.class);
        // tworzenie obiektu SessionFactory
        SessionFactory factory = conf.buildSessionFactory();
        // pobieranie sesji
        Session session = factory.getCurrentSession();

        int id = 34;

        session.beginTransaction();
        System.out.println("Pobieranie obiektu company");
        Company company = session.get(Company.class, id);
        System.out.println("Obiekt company został pobrany");
        System.out.println(company);

        System.out.println("Nieruchomości: ");
        for (Property property : company.getProperties()){
            System.out.println(property);
        }


        session.getTransaction().commit();


        // zakończeine obiektu SessionFactory
        factory.close();

    }
}
