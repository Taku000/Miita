$(window).bind('load', function(){
	//モーダルウィンドウを出現させるクリックイベント
	$("#modal-open").click( function(){

		//キーボード操作などにより、オーバーレイが多重起動するのを防止する
		$( this ).blur() ;	//ボタンからフォーカスを外す
		if( $( "#modal-overlay" )[0] ) return false ;		//新しくモーダルウィンドウを起動しない (防止策1)
		//if($("#modal-overlay")[0]) $("#modal-overlay").remove() ;		//現在のモーダルウィンドウを削除して新しく起動する (防止策2)

		//オーバーレイを出現させる
		$( "body" ).append( '<div id="modal-overlay"></div>' ) ;
		$( "#modal-overlay" ).fadeIn( "slow" ) ;

		//コンテンツをセンタリングする
		centeringModalSyncer() ;

		//コンテンツをフェードインする
		$( "#modal-content" ).fadeIn( "slow" ) ;

		//[#modal-overlay]、または[#modal-close]をクリックしたら…
		$( "#modal-overlay,#modal-close" ).unbind().click( function(){

			//[#modal-content]と[#modal-overlay]をフェードアウトした後に…
			$( "#modal-content,#modal-overlay" ).fadeOut( "slow" , function(){

				//[#modal-overlay]を削除する
				$('#modal-overlay').remove() ;

			});

		});

	} ) ;

	//リサイズされたら、センタリングをする関数[centeringModalSyncer()]を実行する
	$( window ).resize( centeringModalSyncer ) ;

		//センタリングを実行する関数
		function centeringModalSyncer() {

			//画面(ウィンドウ)の幅、高さを取得
			var w = $( window ).width() ;
			var h = $( window ).height() ;

			// コンテンツ(#modal-content)の幅、高さを取得
			// jQueryのバージョンによっては、引数[{margin:true}]を指定した時、不具合を起こします。
	//		var cw = $( "#modal-content" ).outerWidth( {margin:true} );
	//		var ch = $( "#modal-content" ).outerHeight( {margin:true} );
			var cw = $( "#modal-content" ).outerWidth();
			var ch = $( "#modal-content" ).outerHeight();

			//センタリングを実行する
			$( "#modal-content" ).css( {"left": ((w - cw)/2) + "px","top": ((h - ch)/2) + "px"});

		}
})

		var select1 = regist_article.category1; //変数select1を宣言
		var select2 = regist_article.category2; //変数select2を宣言

		//登録画面のカテゴリ選択の能動的変化
select1.addEventListener("change",function changeSelect1()
		{
			select2.options.length = 0; // 選択肢の数がそれぞれに異なる場合、これが重要
			if (select1.options[select1.selectedIndex].value == "kaihatu")
            {
				select2.style.border = '1px solid black';
                select2.options[0] = new Option("Java","Java");
                select2.options[1] = new Option("Java Silver","Java Silver");
				select2.options[2] = new Option("JSP/Servlet","JSP/Servlet");
			}
			else if (select1.options[select1.selectedIndex].value == "koutiku")
            {
				select2.style.border = '1px solid black';
                select2.options[0] = new Option("Linux","Linux");
                select2.options[1] = new Option("CCNA","CCNA");
                select2.options[2] = new Option("AWS","AWS");
				select2.options[3] = new Option("LPIC","LPIC");
			}
			else if (select1.options[select1.selectedIndex].value == "DB")
            {
				select2.style.border = '1px solid black';
				select2.options[0] = new Option("SQL基本文法","SQL_bunpou");
				select2.options[1] = new Option("MySQL","MySQL");
				select2.options[2] = new Option("H2","H2");
				select2.options[3] = new Option("postgreSQL","postgreSQL");
			}
			else if (select1.options[select1.selectedIndex].value == "other")
            {
				select2.style.border = '1px solid black';
				select2.options[0] = new Option("その他","other");
            }
		})

//登録に失敗した場合、モーダルでアラート表示したい

window.onload = function() {
	var popup = document.getElementById('js-popup');
	if(!popup) return;
	popup.classList.add('is-show');

	var popmessage =document.getElementById('js-popmessage');
	popmessage.classList.add('popmessage-elem')

	var text ="初期";

	//登録機能実行時返ってきたエラー内容によって表示変更
	if (error == "miss") {
		text = "パスワードを間違えましたね"
	}else if (error == "categoryNull") {
		text = "カテゴリが未選択ですよ"
	}else if(error == "notQiita"){
		text = "Qiitaの記事ではありませんよ"
	}else if(error == "notfound"){
		text = "記事が見つかりませんよ"
	}else if (error == "duplication" ) {
		text = "その記事は既に登録されてますよ"
	}else if(error == "registFailure"){
		text = "なんか登録に失敗しましたよ"
	}
	popmessage.innerHTML = text;

	var blackBg = document.getElementById('js-black-bg');
	var closeBtn = document.getElementById('js-close-btn');

	closePopUp(blackBg);
	closePopUp(closeBtn);

	function closePopUp(elem) {
	  if(!elem) return;
	  elem.addEventListener('click', function() {
		popup.classList.remove('is-show');
		popmessage.classList.remove('popmessage-elem');
		popmessage.classList.remove('js-popmessage');
	  })
	}
  }

//削除ボタンを押すとモーダル出現
const delbtn = document.getElementsByClassName('delete_button');

for(let i = 0; i < delbtn.length; i++){

	delbtn[i].addEventListener('click', function (){
		
	var deletepopup = document.getElementById('del-js-popup');
	if(!deletepopup) return;
		deletepopup.classList.add('del-is-show');

		var delblackBg = document.getElementById('del-js-black-bg');
		var delcloseBtn = document.getElementById('del-js-close-btn');

		delclosePopUp(delblackBg);
		delclosePopUp(delcloseBtn);

		function delclosePopUp(elem) {
			if(!elem) return;
			elem.addEventListener('click', function() {
			deletepopup.classList.remove('del-is-show');
			})
		}
	},false);
}

