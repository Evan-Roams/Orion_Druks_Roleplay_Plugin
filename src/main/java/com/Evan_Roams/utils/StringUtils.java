package com.Evan_Roams.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    public static String getDisplayNameForKey(String key) {
        switch (key) {
            case "0_0":
                return "0.0 - Conducción sin Licencia";
            case "0_1":
                return "0.1 - Exceso de Velocidad";
            case "0_2":
                return "0.2 - Mal Estacionado";
            case "0_3":
                return "0.3 - Conducción Temerararia";
            case "0_4":
                return "0.4 - Abandono de Vehículo";
            case "0_5":
                return "0.5 - Fuga de la Policía";
            case "0_6":
                return "0.6 - Conducción bajo Efectos de Sustancias";
            case "0_7":
                return "0.7 - Conducción sin Documentación";
            case "0_8":
                return "0.8 - Acceso a Área Restringida";
            case "0_9":
                return "0.9 - Destruccion de propiedad publica con vehiculo";

// Delitos Menores
            case "1_0":
                return "1.0 - Robo a Mano Armada";
            case "1_1":
                return "1.1 - Hurto en Propiedad Privada";
            case "1_2":
                return "1.2 - Vandalismo";
            case "1_3":
                return "1.3 - Daño a Propiedad Pública";
            case "1_4":
                return "1.4 - Consumo de Drogas en Público";
            case "1_5":
                return "1.5 - Tenencia de Marihuana (Uso Personal = 3 items)";
            case "1_6":
                return "1.6 - Construcción sin Permiso (Por Chunk)";
            case "1_7":
                return "1.7 - Irrespeto a la Autoridad";
            case "1_8":
                return "1.8 - Soborno a un Funcionario Público";
            case "1_9":
                return "1.9 - Falsificación de Documentos";

// Delitos Graves
            case "2_0":
                return "2.0 - Cultivo de Marihuana";
            case "2_1":
                return "2.1 - Tráfico de Drogas (Marihuana)";
            case "2_2":
                return "2.2 - Tenencia Ilegal de Armas (Pistola)";
            case "2_3":
                return "2.3 - Uso Ilegal de Explosivos";
            case "2_4":
                return "2.4 - Secuestro";
            case "2_5":
                return "2.5 - Intento de Homicidio";
            case "2_6":
                return "2.6 - Intento de Homicidio a Funcionario";
            case "2_7":
                return "2.7 - Incitación a la Violencia";
            case "2_8":
                return "2.8 - Manipulación o Destrucción de Evidencia";

// Delitos Contra la Autoridad y el Estado
            case "3_0":
                return "3.0 - Resistencia a la Autoridad";
            case "3_1":
                return "3.1 - Desacato Policial";
            case "3_2":
                return "3.2 - Soborno o Intento de Soborno a Policía";
            case "3_3":
                return "3.3 - Abuso de Poder";
            case "3_4":
                return "3.4 - Evasión de Impuestos";
            case "3_5":
                return "3.5 - Contrabando";
            case "3_6":
                return "3.6 - Fuga de Prisión";
            case "3_7":
                return "3.7 - Secuestro de Vehículo";
            case "3_8":
                return "3.8 - Terrorismo";
            case "3_9":
                return "3.9 - Intento de Homicidio a Policía";

// Delitos de Corrupción y Contra el Orden Público
            case "4_0":
                return "4.0 - Soborno a un Funcionario Público";
            case "4_1":
                return "4.1 - Ocultación de un Delito";
            case "4_2":
                return "4.2 - Intimidación a Testigos";
            default:
                return key;
        }
    }

    public static String horaActual(){
        String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return hora;
    }
}
