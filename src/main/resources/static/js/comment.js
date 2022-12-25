let index = {
		init: function(){
			$("#btn-comment-save").on("click", ()=>{
				this.replySave();
			});
		},

		replySave: function(){
            const data = {
			    memberId: $("#commentWriter").val(),
                boardId: $("#boardId").val(),
                content: $("#commentContent").val()
			};
			console.log(data);

			$.ajax({
				type: "POST",
				url: `/board/${data.boardId}/comment`,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
//				dataType: "json"   -- dataType : TEXT 로받아옴
			}).done(function(resp){
				alert("댓글작성이 완료되었습니다.");
				location.reload();
			}).fail(function(error){
			    alert("댓글을 쓰려면 로그인이 필요합니다.");
				location.reload();
			});
		},

		commentDelete: function(boardId, commentId){
			$.ajax({
				type: "DELETE",
				url: `/board/${boardId}/comment/${commentId}`,
			}).done(function(resp){
				alert("댓글이 삭제되었습니다.");
				window.location.reload();
			}).fail(function(error){
				alert(JSON.stringify(error));
			});
		},
}

index.init();