import os
import pymysql
import yaml

db = pymysql.connect(host='',
                     user='',
                     password='',
                     database='')
cursor = db.cursor()

titles = {}
format_id_n = 0

for file_name in os.listdir('Player1'):
    f = open("Player1/" + file_name)
    y = yaml.safe_load(f)
    player_id = file_name.split(".")[0]
    for ch_name in y['chenghao']:
        if ch_name not in titles.keys():
            format_id_n += 1
            format_id = 'old_ch_' + str(format_id_n)
            titles[ch_name] = format_id
            print(format_id + "  " + ch_name)
            cursor.execute(
                "INSERT INTO `medal` (`medal_id`, `medal_name`, `medal_material`,`medal_description`) VALUES (%s,%s,%s,%s)",
                (
                    format_id, ch_name, 'PAPER', '来自老称号'))
        cursor.execute(
            "INSERT INTO `player_medal` (`player_id`, `medal_id`) VALUES (%s,%s)",
            (
                player_id, titles[ch_name]))
db.commit()
