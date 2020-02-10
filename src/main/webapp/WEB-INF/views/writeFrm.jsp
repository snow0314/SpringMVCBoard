<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글을 쓸거야!!!!!!</title>
<style type="text/css">
table{
	width: 100%;
}
table, td, th {
   border: 1px solid;
   border-collapse: collapse;
   padding: 5px;
}
input[type='text']{
   width:100%;
}
textarea {
	width: 100%;
	resize: none;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
<h3>글쓰기</h3>
<form action="boardwrite" id="frm" method="post" enctype="multipart/form-data">
	<table border="1">
		<tr>
			<td>제목</td>
			<td><input type="text" name="b_title" id="b_title"> </td>
		</tr>
		<tr>
			<td>내용</td>
			<td><textarea rows="20" name="b_contents" id="b_contents"></textarea> </td>
		</tr>
		<tr>
			<td>파일첨부</td>
			<td><input type="file" name="files" id="files" multiple="multiple"/>
				<input type="hidden" id="fileCheck" name="fileCheck" value="0"/>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
			<input type="submit" value="글작성"/>
			<input type="button" onclick="formData()" value="FormData"/>
			<input type="reset" id="reset" value="취소" onclick="resetVal()"/>
			<input type="button" onclick="location.href='boardlist'" value="리스트 보기"/>
			</td>
		</tr>
	</table>
</form>
</body>
<script type="text/javascript">

	$("#files").on("change", function(){
		console.log(this.value); //첨부된 파일
		if(this.value===''){
			console.log("empty");
			$("#fileCheck").val(0); //첨부 안됨
		}else{
			console.log("not empty");
			$("#fileCheck").val(1); //첨부됨
		}
		console.log($("#fileCheck").val());
	});

	function resetVal() {
		$("#fileCheck").val(0); //첨부 안됨
		//console.log("empty");
		//console.log($("#fileCheck").val());
	}

	function formData() {
		var $obj=$("#files");
		console.log($obj); //제이쿼리 객체
		console.dir($obj[0]); //js 객체
		//var obj=document.getElementById("files");
		//console.dir(obj); 
		
		console.dir($obj[0]); //파일 엘리먼트
		console.dir($obj[0].files); //첨부된 파일리스트
		console.dir($obj[0].files.length); //첨부된 파일리스트 개수
		console.dir($obj[0].files[0]); //1번째 파일 정보
		console.dir($obj[0].files[1]); //2번째 파일 정보
	
		//FormData 사용 목적
		//1.multipart/form-data를 Ajax전송시 사용(파일 업로드)
		//2.ajax를 이용해서 서버로 넘긴다.(restFul에서 사용함)
		//3.FormData 객체는 form의 일부데이터만 서버에 전송할때고 좋습니다.
		//var formData=new FormData($("#frm")); //폼데이터 객체, 제이쿼리 객체여서 에러난다.
		//var formData=new FormData(document.getElementById("frm")); //폼데이터 객체 , 모두 저장
		//console.log(formData.get("b_title"));
		//console.log(formData.get("b_contents"));
		
		var formData=new FormData(); //선별해서 저장
		formData.append("b_title", $("#b_title").val());
		formData.append("b_contents", $("#b_contents").val());
		formData.append("fileCheck", $("#fileCheck").val()); //0 or 1
		
		var files=$obj[0].files; //첨부된 파일정보
		for(var i=0;i<files.length;i++){
			formData.append("files",files[i]); //Map과 달리 속성(키) 같아도 중복 저장
		}
		console.log("---------------------------------")
		console.log(formData.get("b_title"));
		console.log(formData.getAll("files")); //get으로 가져오면 처음 하나만 보여준다. getAll을 쓰면 저장된 모든 내용을 보여줌
		console.log(formData);
		$.ajax({
			url:"rest/boardwrite",
			type:"post", //enctype=multipart/form-data를 전송시 반드시 post
			data: formData,
			processData:false, //쿼리스트링이 아니니까 까지 말라는 의미, application/x-www-form-urlencoded(쿼리스트링)
			contentType:false, //제이슨 아니니까 까지 말라는 의미, multipart의 경우 contentType을 false
		    dataType:"json", //rest 컨트롤러 이용
		    success:function(data){
		    	alert("성공");
		    	console.dir(data);
		    	location.href="./boardlist"; //restFul에서는 보통 이렇게 포워딩한다.
		    },
		    error:function(error){
		    	alert("실패");
		    	console.log(error);
		    }
		});//ajax End
	}
</script>
</html>