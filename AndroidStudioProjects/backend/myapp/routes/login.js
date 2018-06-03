var mysql      = require('mysql');
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : '970730',
  database : 'SweetDream'
});
 
connection.connect();

var express=require('express');

var router=express.Router();

var logger = require('log4js').getLogger("login");


router.get('/',function(req,res){

    logger.info(req.param('phone'));

    var name = req.param('phone');
    var pwd = req.param('pwd');
    var searchSql = "select * from UserInfo where name ='"+name+"'";
    connection.query(searchSql, function (error, results) {
        if (error) throw error;
        if(results[0]==null){
            var addSql = "insert into UserInfo (name,pwd) values ('"+name+"', '"+pwd+"')";  
            connection.query(addSql, function (error, results) {
            if (error) throw error;
            logger.info('Add New !!');
            var json = JSON.stringify({resultnum:2});
            res.send(json);
            });
        }
        else if(results[0]['pwd']==pwd) {
            logger.info('match !!');
            var json = JSON.stringify({resultnum:1,pic_cnt:results[0]['pic_cnt'],
                                     time1:results[0]['1_time'],time2:results[0]['2_time'],time3:results[0]['3_time'],time4:results[0]['4_time'],
                                     time5:results[0]['5_time'],time6:results[0]['6_time'],time7:results[0]['7_time'],
                                     sleep_hh:results[0]['sleep_hh'],sleep_mm:results[0]['sleep_mm'],
                                     wake_hh:results[0]['wake_hh'],wake_mm:results[0]['wake_mm']
                                    });
            res.send(json);
        }
        else{
            logger.info('Not match !!');
            var json = JSON.stringify({resultnum:0});
            res.send(json);
        }      
        });
   
});

router.post('/',function(req,res){

    logger.info(req.param('phone'));

    var name = req.param('phone');
    var pwd = req.param('pwd');
    var searchSql = "select * from UserInfo where name ='"+name+"'";
    connection.query(searchSql, function (error, results) {
        if (error) throw error;
        if(results[0]==null){
            var addSql = "insert into UserInfo (name,pwd) values ('"+name+"', '"+pwd+"')";  
            connection.query(addSql, function (error, results) {
            if (error) throw error;
            logger.info('Add New !!');
            var json = JSON.stringify({resultnum:2});
            res.send(json);
            });
        }
        else if(results[0]['pwd']==pwd) {
            logger.info('match !!');
            var json = JSON.stringify({resultnum:1,pic_cnt:results[0]['pic_cnt'],
                                     time1:results[0]['1_time'],time2:results[0]['2_time'],time3:results[0]['3_time'],time4:results[0]['4_time'],
                                     time5:results[0]['5_time'],time6:results[0]['6_time'],time7:results[0]['7_time'],
                                     sleep_hh:results[0]['sleep_hh'],sleep_mm:results[0]['sleep_mm'],
                                     wake_hh:results[0]['wake_hh'],wake_mm:results[0]['wake_mm']
                                    });
            res.send(json);
        }
        else{
            logger.info('Not match !!');
            var json = JSON.stringify({resultnum:0});
            res.send(json);
        }      
        });
});

module.exports = router