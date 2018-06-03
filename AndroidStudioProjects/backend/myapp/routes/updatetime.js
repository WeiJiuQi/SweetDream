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

router.get('/',function(req,res){
    console.log(req.body);
    console.log(req.param('user_name'));
    console.log(req.param('field_name1'));
    console.log(req.param('field_value1'));
    console.log(req.param('field_name2'));
    console.log(req.param('field_value2'));

    var user_name = req.param('user_name');
    var field_name1 = req.param('field_name1');
    var field_value1 = req.param('field_value1');
    var field_name2 = req.param('field_name2');
    var field_value2 = req.param('field_value2');
    
    var updateSql = "update UserInfo set "+field_name1+"="+field_value1+","+field_name2+"="+field_value2+" where name='"+user_name+"';";
    connection.query(updateSql, function (error, results) {
        if (error) throw error;
        console.log("Update Success!");
        });  
});

router.post('/',function(req,res){
    console.log(req.body);
    console.log(req.param('user_name'));
    console.log(req.param('field_name1'));
    console.log(req.param('field_value1'));
    console.log(req.param('field_name2'));
    console.log(req.param('field_value2'));

    var user_name = req.param('user_name');
    var field_name1 = req.param('field_name1');
    var field_value1 = req.param('field_value1');
    var field_name2 = req.param('field_name2');
    var field_value2 = req.param('field_value2');
    
    var updateSql = "update UserInfo set "+field_name1+"="+field_value1+","+field_name2+"="+field_value2+" where name="+user_name+";";
    connection.query(updateSql, function (error, results) {
        if (error) throw error;
        console.log("Update Success!");
        });  
});

module.exports = router