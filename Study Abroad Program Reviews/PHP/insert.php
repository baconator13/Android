<?php
	$host='localhost';
	$uname='anderspm';
	$pwd='2twdeS5C';
	$db="anderspm";
    
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
    
    $program=$_REQUEST['program'];
	$id=$_REQUEST['id'];
	$review=$_REQUEST['review'];
    
	$flag['code']=0;
    
	if($r=mysql_query("insert into bacon values('$program','$id','$review') ",$con))
	{
		$flag['code']=1;
        echo "hi this will be the data returned";
	}
    
	print(json_encode($flag));
	mysql_close($con);
?>