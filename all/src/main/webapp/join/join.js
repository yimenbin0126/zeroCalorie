// 전체 유효성 검사
window.onload = function () {
    id_pass();
    name();
    birth();
    gender();
    email();
    tel();
    join();
};

// 아이디, 패스워드 
function id_pass() {
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


// 생년월일
function birth() {
    var birth = document.querySelector("#e_input_birth");
    var error_b = document.querySelector("#e_birth_confirm");

    // 연도 *1992/01/22
    // 유효성 검사
    const regex_b = /^[0-9]*$/;
    
    birth.addEventListener('keyup', function () {
        console.log("1",regex_b.test(birth.value.substring(0,4)))
        console.log("2",regex_b.test(birth.value.substring(5)))
        console.log("3",birth.value.substring(0,4))
        if (regex_b.test(birth.value.substring(0,4)) == true && regex_b.test(birth.value.substring(5,7)) == true
        && regex_b.test(birth.value.substring(8)) == true && birth.value.length == 10
        && birth.value[4] == '/' && birth.value[7] == '/') {
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



// 휴대전화
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


// 가입버튼
function join() {

    document.querySelector('#e_sub_btn').addEventListener('click', (e) => {

        // 아이디
        if (document.querySelector('#e_id_confirm').style.display == "block"
            || document.querySelector('#e_input_id').value.length == 0) {
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
            if (document.querySelector('#e_gender_confirm').style.display == "block"
                || document.querySelector('#e_sel_gender').value == '선택') {
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
                || regex.test(document.querySelector('#e_input_email').value) === false) {
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
                || regex.test(document.querySelector('#e_input_tel').value) === false) {
                    e.preventDefault();
                    alert('전화번호를 제대로 입력해주세요.');
                    document.querySelector('#e_input_tel').focus();
            }
        }
        // 가입 완료
    });
}