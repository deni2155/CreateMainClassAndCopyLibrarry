package com.kindcat.parsexml.propetrties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//
//Класс для работы с файлом свойств
//
public class ConfigFile {

    //
    //Папка с входящей корреспонденцией
    //
    private File in;

    public File getIn() {
        return in;
    }
    //
    //Папка с исходящей корреспонденцией
    //
    private File out;

    File getOut() {
        return out;
    }
    //
    //Архив пакетов
    //
    private File archive;

    File getArchive() {
        return archive;
    }
    //
    //Временная папка приложения
    //
    private File temp;

    File getTemp() {
        return temp;
    }
    public ConfigFile(){
        //подключаю логирование
        Logger logger=LoggerFactory.getLogger(ConfigFile.class);
        //указываю путь к файлу свойств по умолчанию
        File pathConfigFile=new File("resources/config.properties");
        //если файла свойств по умолчанию нет, то указываю путь к файлу свойств для компиляции
        if(!pathConfigFile.exists()){
            pathConfigFile=new File("src/main/resources/config.properties");
        }

        //если ссылка на конфигурационный не содержит значение null
        if(pathConfigFile.exists()){
            try(FileInputStream fileStream=new FileInputStream(pathConfigFile)){
                try{
                    Properties properties=new Properties();
                    properties.load(fileStream);
                    if(properties.getProperty("in") != null){
                        in=new File(properties.getProperty("in"));
                    }
                    else if (properties.getProperty("in") == null){
                       logger.warn(String.format("В конфигурационном файле %s не найдена переменная \"in\". Она должна быть равна ссылке на каталог с входящими пакетами",pathConfigFile));
                    }
                    if(properties.getProperty("out") != null){
                        out=new File(properties.getProperty("out"));
                    }
                    else if (properties.getProperty("out") == null){
                        logger.warn(String.format("В конфигурационном файле %s не найдена переменная \"out\". Она должна быть равна ссылке на каталог с исходящими пакетами",pathConfigFile));
                    }
                    if(properties.getProperty("archive") != null){
                        archive=new File(properties.getProperty("archive"));
                    }
                    else if (properties.getProperty("archive") == null){
                        logger.warn(String.format("В конфигурационном файле %s не найдена переменная \"archive\". Она должна быть равна ссылке на каталог для группировки пакетов и архивирования",pathConfigFile));
                    }
                    if(properties.getProperty("temp") != null){
                        temp=new File(properties.getProperty("temp"));
                    }
                    else if (properties.getProperty("temp") == null){
                        logger.warn(String.format("В конфигурационном файле %s не найдена переменная \"temp\". Она должна быть равна ссылке на временный каталог, который необходим для работы приложения",pathConfigFile));
                    }
                }
                catch(FileNotFoundException ex){
                    logger.error(String.format("При чтении конфигурационного файла %s возникла программная ошибка",pathConfigFile), ex);
                }
                    catch(NullPointerException | IOException ex){
                        logger.error(String.format("При чтении конфигурационного файла %s возникла программная ошибка",pathConfigFile), ex);
                    }
                    finally{
                        fileStream.close();
                    }
                }
                catch(Exception ex){
                    logger.error(String.format("При чтении конфигурационного файла %s возникла программная ошибка",pathConfigFile), ex);
                }
            }
            else{
                logger.info(String.format("Не найден конфигурационный файл %s\nВ файле нужно указать 4 переменные:\n\tin=ссылка на каталог с входящими пакетами\n\tout=ссылка на каталог с исходящими пакетами\n\tarchive=ссылка на каталог для группировки и архивирования пакетов\n\ttemp=ссылка на временную папку для работы приложения",pathConfigFile));
            }
    }
}
