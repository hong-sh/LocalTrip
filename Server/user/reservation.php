<?php 
header("Content-Type:text/html;charset=utf-8");

$con = mysql_connect("localhost", "rkskekfk2020", "Tjdgkr23!");

if(!$con) {
	die('Could not connect:' .mysql_error());
}

mysql_select_db("rkskekfk2020_jeju", $con);
mysql_query("set names utf-8");
mysql_query("set session character_set_connection=utf8;");
mysql_query("set session character_set_results=utf8;");
mysql_query("set session character_set_client=utf8;");

$sql = "INSERT INTO reservation (user_idx, course_idx, reservation_person_number, reservation_date, reservation_price, reservation_name, reservation_phone, reservation_email, reservation_request) VALUES "
		. "($_POST[user_idx], $_POST[course_idx], $_POST[reservation_person_number], '$_POST[reservation_date]', $_POST[reservation_price],'$_POST[reservation_name]', '$_POST[reservation_phone]','$_POST[reservation_email]', '$_POST[reservation_request]')";

if(!mysql_query($sql, $con)) {
	die('Error during Query : ' .mysql_error());
	$result->result = "false";
} else {
	$result->result = "true";
}

echo json_encode($result , JSON_UNESCAPED_UNICODE);

mysql_close($con); 
?>