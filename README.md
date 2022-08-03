# csv-sorter
The application support the following cmd options:
| CLI option (long) | CLI option (short)   | Arguments | Default   | Required   | Description                              |
| ------            | ------                | ------    | ------    | ------    |------------------------------------------|
| --help            |                      |           |           | false     | Print application usage                  |
| --input           | -i                   | String    |           | true      | Path of the data CSV file                |   
| --output          | -o                   | String    |           | true      | Path of the output directory             |
| --filters         | -f                   | String    |           | true      | Path of the filter CSV file              |
| --column          | -c                   | String    | labels    | false     | Name of the column with labels           |
| --delimiter       | -d                   | String    | ,         | false     | Delimiter used by CSV files              |
| --charset         |                      | String    | UTF-8     | false     | Charset used by input (and output) files |

### Input Format
The application can work with any ``input`` CSV file compliant with the following conditions:

- First line in the file is a header (columns are named)  
- File has a column with space-separated labels.

The following is an example of an ``input`` CSV file

```csv
name,date_published,hits,labels
Australian Fauna,11.8.2021,123,snakes spiders animals australia danger
Danger Noodles,9.1.2022,42,snakes anaconda viper
In the Skies,8.4.1994,1432,ravens birds
Too Lazy, 9.11.2019, 5322,pandas bamboo endangered fluffy
Man's Best Friend, 13.4.2014,943,animals dogs people history
```

Beside the ``input`` CSV file, the application also requires a CSV file with ``filters``.  
The schema for the ``filters`` CSV file can be seen in the following example

```csv
name,expression
birds,birds | parrots | ravens
eggs,turtles | snakes | birds
social,society | people & animals
```
The first column ``name`` is the name of the filter. This value is also used as the name of the output CSV file (e.g.,``eggs.csv``).    
The second column ``expression`` is a logical expression.

### Logical Expression Grammar
The following is the [EBNF grammar](https://en.wikipedia.org/wiki/Extended_Backus–Naur_form) of the supported expression language.

```text
expr    =   label | expr, op, expr | not, expr ;
not     =   "!" ;
op      =   space, ( "&" | "|" ), space ;
label   =   ? one or more alphanumerical characters ? ;
space   =   ? zero or more space characters ? ; 
```
