package pl.lukas.fetchTypes;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.lukas.fetchTypes.entity.Company;
import pl.lukas.fetchTypes.entity.CompanyDetail;
import pl.lukas.fetchTypes.entity.Property;

public class CloseSessionApp {

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

        session.getTransaction().commit();


        // dla FetchType typu LAZY trzeba uruchomić dodatkową sesję,
        // LAZY jest dużo szybsze od EAGER, ale wymaga obudowania tego dodatkową sesją jeśli wywołujemy
        // ją po zakończeniu jednej sesji. W przypadku EAGER można zamknąć sesję i wypisać interesującą nas zawartośc
        session = factory.getCurrentSession();

        session.beginTransaction();
        Company mergeCompany = (Company) session.merge(company);
        System.out.println("...");

        System.out.println("Nieruchomości: ");
        for (Property property : mergeCompany.getProperties()){
            System.out.println(property);
        }
        session.getTransaction().commit();

        // zakończeine obiektu SessionFactory
        factory.close();
    }
}
