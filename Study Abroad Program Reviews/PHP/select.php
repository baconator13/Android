<?php
	$host='localhost';
	$uname='anderspm';
	$pwd='2twdeS5C';
	$db="anderspm";
    
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
    
    $program=$_REQUEST['program'];
    
    

	$r=mysql_query("select * from bacon where program='$program'",$con);
    $flag=array();

	while($row=mysql_fetch_array($r))
	{
        $flag["review"]=$row["review"];
        $flag["username"]=$row["id"];
	}
    print(json_encode($flag));
	mysql_close($con);
?>