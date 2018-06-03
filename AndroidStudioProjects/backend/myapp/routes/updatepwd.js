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

var logger = require('log4js').getLogger("updatepwd");

router.get('/',function(req,res){

    logger.info(req.param('user_name'));

    var user_name = req.param('user_name');
    var pwd = req.param('pwd');
   
    var updateSql = "update UserInfo set pwd='"+pwd+"' where name='"+user_name+"';";
    connection.query(updateSql, function (error, results) {
        if (error) {
            logger.error("error!!");
            throw error;
        }
        logger.info("Update PWD Success!");
        });  
});

router.post('/',function(req,res){
    
    logger.info(req.param('user_name'));

    var user_name = req.param('user_name');
    var pwd = req.param('pwd');
   
    var updateSql = "update UserInfo set pwd='"+pwd+"' where name='"+user_name+"';";
    connection.query(updateSql, function (error, results) {
        if (error) {
            logger.error("error!!");
            throw error;
        }
        logger.info("Update PWD Success!");
        });  
});

module.exports = router