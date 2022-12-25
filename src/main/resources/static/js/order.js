let index = {
		init: function(){
			$("#btn-order").on("click", ()=>{
				this.order();
			});
		},

		order: function(){
		    alert("장바구니에 상품을 담았습니다.");
		    }

    }
index.init();
