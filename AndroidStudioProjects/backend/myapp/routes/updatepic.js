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

var logger = require('log4js').getLogger("update pic_cnt");

router.get('/',function(req,res){

    logger.info(req.param('user_name'));

    var user_name = req.param('user_name');
    var pic_cnt = req.param('pic_cnt');
   
    var updateSql = "update UserInfo set pic_cnt="+pic_cnt+" where name='"+user_name+"';";
    connection.query(updateSql, function (error, results) {
        if (error) throw error;
        logger.info("Update Pic_cnt Success!");
        });  
});

router.post('/',function(req,res){

    logger.info(req.param('user_name'));

    var user_name = req.param('user_name');
    var pic_cnt = req.param('pic_cnt');
   
    var updateSql = "update UserInfo set pic_cnt="+pic_cnt+" where name='"+user_name+"';";
    connection.query(updateSql, function (error, results) {
        if (error) throw error;
        logger.info("Update Pic_cnt Success!");
        });  
});

module.exports = router