import java.util.Map;

public interface Convertor {
     default double toILS(final String cName, final Map<String,Double> rates,double num){
         //default implementation to convert a given currency to ils
        double ilsVal = rates.get(cName);
         System.out.println(num/ilsVal);
        return num/ilsVal;

    }
    double convert(final String cName, final Map<String,Double> rates,double num);
}
