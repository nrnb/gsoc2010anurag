package data;

/**
 * Acts as a data structure to store the species data as parsed from file species_all.txt
 * @author Anurag Sharma, the user
 */
public class Species {

    public String code, name;

    /**
     * creates new object
     */
    public Species() {
        code = "";
        name = "";
    }


    /**
     * creates new species object with supplied name and code
     * @param code the species code
     * @param name the species name
     */
    public Species(String code, String name) {
        this.code = code;
        this.name = name;

    }

    @Override
    public String toString() {
        return "[S:" + code + "," + name + "]";
    }
}
