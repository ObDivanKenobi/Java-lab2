package classes;

import classes.DataModels.*;

import java.io.*;
import java.util.function.Predicate;

/**
 * Created by Илья on 21.05.2017.
 */
public class FileHandler {
    public static GoodsList ReadData(String filename){
        File file = new File(filename);
        GoodsList data = new GoodsList();

        if (!file.exists())
            return data;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsoluteFile()), "UTF8"));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    String[] values = s.split("[|]");
                    //если число значений в строке не совпадает с таковым ни для одного типа
                    if (values.length < 6 || values.length > 8)
                        continue;

                    int id, price;
                    try {
                        id = Integer.parseInt(values[0]);
                        price = Integer.parseInt(values[2]);
                    }
                    //если id или price не удалось преобразовать к int, бросаем эту строку
                    catch(NumberFormatException e) {
                        continue;
                    }

                    //если ID меньше нуля или элемент с таким ID уже существует, пробрасываем текущий
                    if (id <= 0 || data.existsId(id)) {
                        continue;
                    }

                    String name = values[1];
                    GoodsTypes type;
                    try {
                        type = GoodsTypes.valueOf(values[3]);
                    }
                    catch (IllegalArgumentException e) {
                        continue;
                    }

                    switch (values.length) {
                        case 6: {
                            ArmorTypes armorType;
                            try {
                                armorType = ArmorTypes.valueOf(values[4]);
                            }
                            catch (IllegalArgumentException e) {
                                continue;
                            }

                            String description = values[5];
                            data.addItem(new Armor(id, name, price, type, armorType, description));
                            break;
                        }
                        case 7: {
                            String shape = values[4],
                                   material = values[5],
                                   description = values[6];

                            data.addItem(new Shield(id, name, price, type, shape, material, description));
                            break;
                        }
                        case 8: {
                            float bladeLength, fullLength, guardWidth, weight;
                            try {
                                bladeLength = Float.parseFloat(values[4]);
                                fullLength = Float.parseFloat(values[5]);
                                guardWidth = Float.parseFloat(values[6]);
                                weight = Float.parseFloat(values[7]);
                            }
                            catch(NumberFormatException e) {
                                continue;
                            }

                            data.addItem(new Sword(id, name, price, type, bladeLength, fullLength, guardWidth, weight ));
                            break;
                        }
                    }
                }
            }
            finally {
                Goods.setMaxID(data.getMaxID());
                in.close();
            }
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }

        return data;
    }

    public static void WriteData(String filename, GoodsList data) {
        File file = new File(filename);
        try {
            if (!file.exists())
                file.createNewFile();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.count(); ++i)
                sb.append(data.getItem(i).toString());

            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try{
                out.print(sb);
            }
            finally {
                out.close();
            }
        }
        catch(IOException e) {
            new RuntimeException(e);
        }
    }
}
