import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class VolcanoAnalyzer {
    private List<Volcano> volcanos;

    public void loadVolcanoes(Optional<String> pathOpt) throws IOException, URISyntaxException {
        try{
            String path = pathOpt.orElse("volcano.json");
            URL url = this.getClass().getClassLoader().getResource(path);
            String jsonString = new String(Files.readAllBytes(Paths.get(url.toURI())));
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            volcanos = objectMapper.readValue(jsonString, typeFactory.constructCollectionType(List.class, Volcano.class));
        } catch(Exception e){
            throw(e);
        }
    }

    public Integer numbVolcanoes(){
        return volcanos.size();
    }

    //add methods here to meet the requirements in README.md

    public List<Volcano> eruptedInEighties(){
        return volcanos.stream().filter(item -> item.getYear() >=1980 && item.getYear() <1990).collect(Collectors.toList());
    } 

    public List<String> highVEI (){
        
        List<String> arr = volcanos.stream().filter(i -> i.getVEI() >=6).map(i->i.getName())
        .collect(Collectors.toList());
        
        return arr;   
    } 

    public Volcano mostDeadly(){
        return volcanos.stream().max(Comparator.comparingInt(d -> Integer.parseInt(d.getDEATHS().isEmpty() ? "0" : d.getDEATHS()))).orElse(null);
    
    }
    public double causedTsunami(){
        return volcanos.stream().filter(s -> !s.getTsu().equals("")).toList().size() *100 / volcanos.size();
    }

    public String mostCommonType(){
        List<String> volcanoTypes =  volcanos.stream().map(type -> type.getType()).distinct().collect(Collectors.toList());
        List<Integer> frequency = new ArrayList<>();
        volcanoTypes.forEach(obj -> {
            frequency.add(volcanos.stream().filter(item -> item.getType().equals(obj)).collect(Collectors.toList()).size());
        });
        Integer highestFrequency = Collections.max(frequency);
        return volcanoTypes.get(frequency.indexOf(highestFrequency))
        ;}

    public int eruptionsByCountry(String country){
        return volcanos.stream().filter(i -> i.getCountry().equals(country)).collect(Collectors.toList()).size();
    }

    public double averageElevation(){
        // return
        double totalNoOfElevations = volcanos.size();
        double sumOfElevations = volcanos.stream().mapToDouble(i -> i.getElevation()).sum();
        return sumOfElevations /totalNoOfElevations;
    }
    
    public List<String> volcanoTypes(){
        return volcanos.stream().map(types -> types.getType()).distinct().collect(Collectors.toList());
    }

    public double percentNorth() {
        double NorthernHemispherCountries= volcanos.stream().filter(n -> n.getLatitude() > 0).count();
        int totalNoOfCountries = volcanos.size();

    //     double totalNoOfCountries = volcanos.stream().map(country -> country.getLatitude()).count();
    //     // return NorthernHemispherCountries *100 /totalNoOfCountries;
        return NorthernHemispherCountries *100 / totalNoOfCountries; // 804
        // return NorthernHemispherCountries;
    }

    public List<String> manyFilters(){
        return volcanos.stream().filter(item -> item.getYear() >1800 && !item.getTsu().equals("tsu") && item.getLatitude() <0 && item.getVEI() == 5).map(item -> item.getName()).collect(Collectors.toList());
    }

    // Return the names of eruptions that occurred at or above an elevation passed in as an argument.

    // public List<String> elevatedVolcanoes(int Elevation){
    //     return volcanos.stream().filter(null)

    // }

}
