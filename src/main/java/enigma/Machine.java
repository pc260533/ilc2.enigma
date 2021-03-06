package enigma;

/**
 * Machine est la classe représentant une machine Enigma : elle contient 3 rotors et un reflecteur.
 * 
 * @author Pierre-Nicolas
 */
public class Machine {
    
    /**
     * Le rotor de gauche de la machine enigma.
     */
    private Rotor leftRotor;
    /**
     * Le rotor du milieu de la machine enigma.
     */
    private Rotor middleRotor;
    /**
     * Le rotor de droite de la machine enigma.
     */
    private Rotor rightRotor;
    /**
     * Le réflécteur de la machine enigma.
     */
    private Reflector reflector;

    /**
     * Initialiser les rotors de la machien enigma.
     * @param reflector Le reflécteur.
     * @param left Le rotor de gauche.
     * @param middle Le rotor du milieu.
     * @param right Le rotor de droite.
     */
    public void initRotors(Reflector reflector, Rotor left, Rotor middle, Rotor right) {
        this.reflector = reflector;
        leftRotor = left;
        middleRotor = middle;
        rightRotor = right;
    }

    /**
     * Modifier les positions du réflécteur et des rotors.
     * @param setting Les différentes positions du réflécteur et des rotors.
     */
    public void setPositions(String setting) {
        char[] charSettings = setting.toCharArray();
        reflector.setPosition(Rotor.toIndex(charSettings[0]));
        leftRotor.setPosition(Rotor.toIndex(charSettings[1]));
        middleRotor.setPosition(Rotor.toIndex(charSettings[2]));
        rightRotor.setPosition(Rotor.toIndex(charSettings[3]));
    }

    public void configure(Reflector reflector, Rotor left, Rotor middle, Rotor right, String setting) {
        this.initRotors(reflector, left, middle, right);
        this.setPositions(setting);
    }

    public String convert(String msg) {
        msg = msg.toUpperCase();
        char[] msgChars = msg.toCharArray();
        String result = "";
        for (char c : msgChars) {
            result += convertChar(c);
        }
        return result;
    }

    char convertChar(char c) {
        advanceRotors();
        int charIndex = Rotor.toIndex(c);
        int output;
        output = rightRotor.convertForward(charIndex);
        output = middleRotor.convertForward(output);
        output = leftRotor.convertForward(output);
        output = reflector.convertForward(output);
        output = leftRotor.convertBackward(output);
        output = middleRotor.convertBackward(output);
        output = rightRotor.convertBackward(output);
        return Rotor.toLetter(output);

    }

    void advanceRotors() {
        boolean advanceLeft = false;
        boolean advanceMiddle = false;
        boolean advanceRight = true;
        if (leftRotor.atNotch()) {
        }
        if (middleRotor.atNotch()) {
            advanceMiddle = true;
            advanceLeft = true;
        }
        if (rightRotor.atNotch()) {
            advanceMiddle = true;
            advanceRight = true;
        }
        if (advanceLeft) {
            leftRotor.advance();
        }
        if (advanceRight) {
            rightRotor.advance();
        }
        if (advanceMiddle) {
            middleRotor.advance();
        }
    }
}
