// 전체 유효성 검사
window.onload = function () {
    if(document.querySelector('#e_idCheck').style.display =="block"){
    }
    id_pass();
    name();
    nickname();
    birth();
    gender();
    email();
    height();
    tel();
    pro_file();
    join();
};

// 아이디, 패스워드 
function id_pass() {

	
	// 아이디 중복확인
	// 아이디 중복확인 버튼 - 클릭
	document.querySelector('#e_idCheck').addEventListener('click', function () {
		if(document.querySelector('#e_id_check').style.display =="none"
		&& document.querySelector('#e_input_id').value.length != 0){
			$.ajax({
		        url: "/all/join",
		        type:"POST",
		        data: {
		            e_idcheck_click : "Y",
		            e_input_id : document.querySelector('#e_input_id').value
		        },
		        success : function(data){
		        	if(data==0){
		        		document.querySelector('#e_id_check').style.display ="block";
		        	} else {
		        		alert('이미 존재하는 아이디 입니다.');
		        	}
		        },
		        });
		} 
	});
	
	// 아이디 중복 확인 후 건드렸을 때
	document.querySelector('#e_input_id').addEventListener('keyup', function () {
			document.querySelector('#e_id_check').style.display ="none";
	});
	
    // 대상, 에러
    var contents = [document.querySelector('#e_input_id'), document.querySelector('#e_input_pass'), document.querySelector('#e_input_pass_more')];
    var confirms = [document.querySelector('#e_id_confirm'), document.querySelector('#e_pass_confirm'), document.querySelector('#e_pass_more_confirm')]

    // 아이디, 비밀번호 입력 확인 (1) - 가능한 단어, 순서상관X, 총 갯수
    const regex_ip = [(/^[a-z0-9]{5,9}$/), (/^[a-zA-Z0-9!@#*]{5,9}$/)]
    // 아이디, 비밀번호 입력 확인 (2) - 필수 단어, 비밀번호 특수문자 포함여부
    const regex_ip2 = [(/[a-z]*[0-9]/), (/[a-z]*[A-Z]*[0-9]*[@!#*]/)]

    // 유효성 검사 - 아이디
    contents[0].addEventListener('focus', function () {
        contents[0].addEventListener('keyup', function () {
            if (regex_ip[0].test(contents[0].value) == true && regex_ip2[0].test(contents[0].value) == true) {
                confirms[0].style.display = "none";
            } else {
                confirms[0].style.display = "block";
            }
        });
    });

    // 유효성 검사 - 비밀번호
    contents[1].addEventListener('focus', function () {
        contents[1].addEventListener('keyup', function () {
            if (regex_ip[1].test(contents[1].value) == true && regex_ip2[1].test(contents[1].value) == true) {
                confirms[1].style.display = "none";
            } else {
                confirms[1].style.display = "block";
            }
        });
    });

    // 유효성 검사 - 비밀번호 확인
    contents[2].addEventListener('focus', function () {
        contents[2].addEventListener('keyup', function () {
            if (contents[2].value == contents[1].value) {
                confirms[2].style.display = "none";
            } else {
                confirms[2].style.display = "block";
            }
        });
    });

}

// 이름
function name() {
    var name = document.querySelector("#e_input_name");
    var error_n = document.querySelector("#e_name_confirm");

    // 한글만 쓸 수 있음
    // 유효성 검사
    const regex_n = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,3}$/;

    name.addEventListener('keyup', function () {
        if (regex_n.test(name.value) == true) {
            error_n.style.display = "none";
        } else {
            error_n.style.display = "block";
        }
    });
}

// 닉네임
function nickname() {
    var nickname = document.querySelector("#e_input_nickname");
    var error_nn = document.querySelector("#e_nickname_confirm");

    // 한글만 쓸 수 있음
    // 유효성 검사
    const regex_nn = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,5}$/;

    nickname.addEventListener('keyup', function () {
        if (regex_nn.test(nickname.value) == true) {
            error_nn.style.display = "none";
        } else {
            error_nn.style.display = "block";
        }
    });
    
    // 중복확인
	// 중복확인 버튼 - 클릭
	document.querySelector('#e_nickCheck').addEventListener('click', function () {
		if(document.querySelector('#e_nick_check').style.display =="none"
		&& document.querySelector('#e_input_nickname').value.length != 0){
			$.ajax({
		        url: "/all/join",
		        type:"POST",
		        data: {
		            e_nickCheck_click : "Y",
		            e_input_nick : document.querySelector('#e_input_nickname').value
		        },
		        success : function(data){
		        	if(data==0){
		        		document.querySelector('#e_nick_check').style.display ="block";
		        	} else {
		        		alert('이미 존재하는 닉네임 입니다.');
		        	}
		        },
		    });
		} 
	});
	
	// 중복 확인 후 건드렸을 때
	document.querySelector('#e_input_nickname').addEventListener('keyup', function () {
			document.querySelector('#e_nick_check').style.display ="none";
	});
}


// 생년월일
function birth() {
    var birth = document.querySelector("#e_input_birth");
    var error_b = document.querySelector("#e_birth_confirm");

    // 연도 *1992-01-22
    // 유효성 검사
    const regex_b = /^[0-9]*$/;
    
    birth.addEventListener('keyup', function () {
        if (regex_b.test(birth.value.substring(0,4)) == true && regex_b.test(birth.value.substring(5,7)) == true
        && regex_b.test(birth.value.substring(8)) == true && birth.value.length == 10
        && birth.value[4] == '-' && birth.value[7] == '-') {
            error_b.style.display = "none";
        } else {
            error_b.style.display = "block";
        }
    });
}


// 성별
function gender() {
    let gender = document.querySelector('#e_sel_gender');
    let error_g = document.querySelector('#e_gender_confirm')

    gender.addEventListener('click', () => {
        if (gender.value == 'e_none') {
            error_g.style.display = "block";
        } else {
            error_g.style.display = "none";
        }
    })

}


// 이메일
function email() {
    let email = document.querySelector('#e_input_email');
    let error_e = document.querySelector('#e_email_confirm');

    // 이메일 형식 확인 - 알파벳+숫자@알파벳+숫자.알파벳+숫자
    const regex_e = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

    email.addEventListener('keyup', function () {
        if (regex_e.test(email.value) === false) {
            error_e.style.display = "block";
        } else {
            error_e.style.display = "none";
        }
    });

}


// 전화번호
function tel() {
    let phone = document.querySelector('#e_input_tel');
    let error_t = document.querySelector('#e_tel_confirm');

    // 휴대전화 형식 확인 - 01012345678
    const regex_t1 = /^01([0|1|6|7|8|9])$/;
    const regex_t2 = /^[0-9]*$/;

    phone.addEventListener('keyup', function () {
        if (regex_t1.test(phone.value.substring(0,3)) == true && phone.value.length == 13
        && regex_t2.test(phone.value.substring(4,8)) == true && regex_t2.test(phone.value.substring(9)) == true
        && phone.value[3] == '-' && phone.value[8] == '-') {
            error_t.style.display = "none";
        } else {
            error_t.style.display = "block";
        }
    });
}

// 키
function height() {
    var height = document.querySelector("#e_input_height");
    var error_h = document.querySelector("#e_height_confirm");

    // 숫자만 쓸 수 있음
    // 유효성 검사
    const regex_h = /^[0-9]{3,3}$/;

    height.addEventListener('keyup', function () {
        if (regex_h.test(height.value) == true
        && height.value > 100 && height.value < 200) {
            error_h.style.display = "none";
        } else {
            error_h.style.display = "block";
        }
    });
}

// 프로필 사진
function pro_file() {
	// 유효성 검사 초기화
	document.querySelector('#e_pro_img_confirm').style.display = "block";
	
	// 선언 - input 파일, 파일 이미지 뷰, 파일이름
	var input_pro = document.querySelector('#e_input_pro_img');
	var e_view = document.querySelector('.e_pro_img_view img');
	var e_name = document.querySelector('.e_pro_img_name');
	
	// 이미지 미리보기
	input_pro.addEventListener("change",()=>{
		// input 에 파일이 있는 경우 (첫번째 파일)
		if (input_pro.files && input_pro.files[0]) {
			// 파일 객체를 읽어줌
		    var reader = new FileReader();
		    reader.onload = function(e) {
				e_name.innerHTML = "파일명 : "+input_pro.files[0].name;
		      	e_view.src = e.target.result;
		      	document.querySelector('#e_pro_img_confirm').style.display = "none";
	    	};
	    	reader.readAsDataURL(input_pro.files[0]);
		  } else {
		  		e_name.innerHTML = "";
		    	e_view.src = "";
		    	document.querySelector('#e_pro_img_confirm').style.display = "block";
		  }	
	});
    
    // 삭제 시
    document.querySelector('#e_delete_file').addEventListener('click',()=>{
    	input_pro.value = "";
    });
}

// 가입버튼
function join() {

    document.querySelector('#e_sub_btn').addEventListener('click', (e) => {

        // 아이디
        if (document.querySelector('#e_id_confirm').style.display == "block"
            || document.querySelector('#e_input_id').value.length == 0
            || document.querySelector('#e_id_check').style.display == "none") {
                e.preventDefault();
                alert("아이디를 제대로 입력해주세요.");
                document.querySelector('#e_input_id').focus();
        } else {
            p_check();
        }

        // 비밀번호
        function p_check() {
            if (document.querySelector('#e_pass_confirm').style.display == "block"
                || document.querySelector('#e_input_pass').value.length == 0) {
                    e.preventDefault();
                    alert('비밀번호를 제대로 입력해주세요.');
                    document.querySelector('#e_input_pass').focus();
            } else {
                p_more_check();
            }
        }


        // 비밀번호 확인
        function p_more_check() {
            if (document.querySelector('#e_pass_more_confirm').style.display == "block"
                || document.querySelector('#e_input_pass_more').value.length == 0) {
                    e.preventDefault();
                    alert('입력한 비밀번호가 맞지 않습니다.');
                    document.querySelector('#e_input_pass_more').focus();
            } else {
                n_check();
            }
        }

        // 이름
        function n_check() {
            if (document.querySelector('#e_name_confirm').style.display == "block"
                || document.querySelector('#e_input_id').value.length == 0) {
                    e.preventDefault();
                    alert('이름을 입력해주세요.');
                    document.querySelector('#e_input_id').focus();
            } else {
                nick_check();
            }
        }
        
        // 닉네임
        function nick_check() {
            if (document.querySelector('#e_nickname_confirm').style.display == "block"
                || document.querySelector('#e_input_nickname').value.length == 0
                || document.querySelector('#e_nick_check').style.display == "none") {
                    e.preventDefault();
                    alert('닉네임을 입력해주세요.');
                    document.querySelector('#e_input_nickname').focus();
            } else {
                b_check();
            }
        }

        // 생년월일
        function b_check() {
            if (document.querySelector('#e_birth_confirm').style.display == "block"
                || document.querySelector('#e_input_birth').value.length == 0) {
                    e.preventDefault();
                    alert('생년월일을 입력해주세요.');
                    document.querySelector('#e_input_birth').focus();
            } else {
                g_check();
            }
        }

        // 성별
        function g_check() {
        	var g_selectbox = document.querySelector('#e_sel_gender');
        	var g_select = g_selectbox.options[g_selectbox.selectedIndex].value;
            if (document.querySelector('#e_gender_confirm').style.display == "block"
                || g_select == 'e_none') {
                    e.preventDefault();
                    alert('성별을 선택해주세요.');
                    document.querySelector('#e_sel_gender').focus();
            } else {
                e_check();
            }
        }

        // 이메일
        function e_check() {
            const regex_ee = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

            if (document.querySelector('#e_email_confirm').style.display == "block"
                || regex_ee.test(document.querySelector('#e_input_email').value) === false) {
                    e.preventDefault();
                    alert('이메일을 제대로 입력해주세요.');
                    document.querySelector('#e_input_email').focus();
            } else {
                t_check();
            }
        }

        // 휴대전화
        function t_check() {
            const regex_tt = /^01([0|1|6|7|8|9])-?([0-9]{4})-?([0-9]{4})$/;

            if (document.querySelector('#e_tel_confirm').style.display == "block"
                || regex_tt.test(document.querySelector('#e_input_tel').value) === false) {
                    e.preventDefault();
                    alert('전화번호를 제대로 입력해주세요.');
                    document.querySelector('#e_input_tel').focus();
            } else {
            	height();
            }
        }
        
        // 키
        function height() {
            if (document.querySelector('#e_height_confirm').style.display == "block"
                || document.querySelector('#e_input_height').value.length == 0) {
                    e.preventDefault();
                    alert('키를 입력해주세요.');
                    document.querySelector('#e_input_height').focus();
            } 
            else {
            	file();
            }
        }
        
        // 프로필 사진
        function file() {
			if(document.querySelector('#e_input_pro_img').value == ""){
				document.querySelector('#e_pro_img_confirm').style.display = "block";
				e.preventDefault();
                alert('프로필 사진을 입력해주세요.');
                document.querySelector('#e_input_pro_img').focus();
			} else {
            	join();
            }
		}
        
        // 가입 완료
        function join() {
            var e_mainform = document.e_mainform;
            e_mainform.method="post";
            e_mainform.action="/all/joinComplet";
            e_mainform.submit();
            alert("회원가입이 완료되었습니다.");
        }
    });
}