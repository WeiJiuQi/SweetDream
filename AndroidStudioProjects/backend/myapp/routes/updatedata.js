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

var logger = require('log4js').getLogger("update data");

router.get('/',function(req,res){

    logger.info(req.param('user_name'));

    var user_name = req.param('user_name');
    var time1 = req.param('time1');
    var time2 = req.param('time2');
    var time3 = req.param('time3');
    var time4 = req.param('time4');
    var time5 = req.param('time5');
    var time6 = req.param('time6');
    var time7 = req.param('time7');
 
    var updateSql = "update UserInfo set 1_time="+time1+",2_time="+time2+",3_time="+time3+",4_time="+time4+",5_time="+time5+",6_time="+time6+",7_time="+time7+" where name='"+user_name+"';";
    connection.query(updateSql, function (error, results) {
        if (error) throw error;
        logger.info("Update Data Success!");
        });  
});

router.post('/',function(req,res){
    
    logger.info(req.param('user_name'));

    var user_name = req.param('user_name');
    var time1 = req.param('time1');
    var time2 = req.param('time2');
    var time3 = req.param('time3');
    var time4 = req.param('time4');
    var time5 = req.param('time5');
    var time6 = req.param('time6');
    var time7 = req.param('time7');
 
    var updateSql = "update UserInfo set 1_time="+time1+",2_time="+time2+",3_time="+time3+",4_time="+time4+",5_time="+time5+",6_time="+time6+",7_time="+time7+" where name='"+user_name+"';";
    connection.query(updateSql, function (error, results) {
        if (error) throw error;
        logger.info("Update Data Success!");
        });  
});

module.exports = router