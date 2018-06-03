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

var logger = require('log4js').getLogger("user is exist");

router.get('/',function(req,res){

    logger.info(req.param('user_name'));

    var user_name = req.param('user_name');
    var searchSql = "select * from UserInfo where name='"+user_name+"';";
    connection.query(searchSql, function (error, results) {
    if (error) {
        logger.error('error!');
        var json = JSON.stringify({resultNum:2});
        res.send(json);
    }
    else if (results[0]==null){
        logger.info('Not exist!');
        var json = JSON.stringify({resultNum:0});
        res.send(json);
    }
    else{
        logger.info('Exist!');
        var json = JSON.stringify({resultNum:1});
        res.send(json);
    }
    
    });
});

router.post('/',function(req,res){

    logger.info(req.param('user_name'));

    var user_name = req.param('user_name');
    var searchSql = "select * from UserInfo where name='"+user_name+"';";
    connection.query(searchSql, function (error, results) {
    if (error) {
        logger.error('error!');
        var json = JSON.stringify({resultNum:2});
        res.send(json);
    }
    else if (results[0]==null){
        logger.info('Not exist!');
        var json = JSON.stringify({resultNum:0});
        res.send(json);
    }
    else{
        logger.info('Exist!');
        var json = JSON.stringify({resultNum:1});
        res.send(json);
    }
    
    });
});

module.exports = router