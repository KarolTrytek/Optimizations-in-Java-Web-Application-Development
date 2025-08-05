package pl.edu.pk.optimizationsapp.utils;

public class StringUtils {

    public static String normalizePolishToAscii(String str) {
        char[] znaki = str.toCharArray();

        for(int i = 0; i < znaki.length; ++i) {
            if (Character.isLetterOrDigit(znaki[i])) {
                switch (znaki[i]) {
                    case 'Ó':
                        znaki[i] = 'O';
                        break;
                    case 'ó':
                        znaki[i] = 'o';
                        break;
                    case 'Ą':
                        znaki[i] = 'A';
                        break;
                    case 'ą':
                        znaki[i] = 'a';
                        break;
                    case 'Ć':
                        znaki[i] = 'C';
                        break;
                    case 'ć':
                        znaki[i] = 'c';
                        break;
                    case 'Ę':
                        znaki[i] = 'E';
                        break;
                    case 'ę':
                        znaki[i] = 'e';
                        break;
                    case 'Ł':
                        znaki[i] = 'L';
                        break;
                    case 'ł':
                        znaki[i] = 'l';
                        break;
                    case 'Ń':
                        znaki[i] = 'N';
                        break;
                    case 'ń':
                        znaki[i] = 'n';
                        break;
                    case 'Ś':
                        znaki[i] = 'S';
                        break;
                    case 'ś':
                        znaki[i] = 's';
                        break;
                    case 'Ź':
                    case 'Ż':
                        znaki[i] = 'Z';
                        break;
                    case 'ź':
                    case 'ż':
                        znaki[i] = 'z';
                }
            } else {
                znaki[i] = ' ';
            }
        }

        return String.valueOf(znaki);
    }

}
