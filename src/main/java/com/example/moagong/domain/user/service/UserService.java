package com.example.moagong.domain.user.service;

import com.example.moagong.domain.model.Birth;
import com.example.moagong.domain.model.Email;
import com.example.moagong.domain.model.dto.BirthResponse;
import com.example.moagong.domain.user.dto.request.ChangeInfoRequestDto;
import com.example.moagong.domain.user.dto.request.ChangePasswordRequestDto;
import com.example.moagong.domain.user.dto.request.UserRequestDto;
import com.example.moagong.domain.user.dto.response.InformationResponseDto;
import com.example.moagong.domain.user.entity.User;
import com.example.moagong.domain.user.repository.UserRepository;
import com.example.moagong.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.moagong.global.exception.error.UserErrorCode.*;

@Service
@RequiredArgsConstructor //final 등 필요한 값이 붙은 필드만 받는 생성자를 만들어줌
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserRequestDto dto){
        //1. 아이디 중복 확인하기
        if(userRepository.existsById(dto.getUser_id())){
            //예외 처리 -> 아이디 중복
            throw new CustomException(DUPLICATE_USER_ID);
        }

        //이메일 바꿔주기
        Email email = Email.of(dto.getEmail());
        Birth birth = Birth.of(dto.getBirth().getYear(),dto.getBirth().getMonth(),dto.getBirth().getDay());

        //2. 다른 정보 저장하기
        User user = User.builder()
                .id(dto.getUser_id())
                .password(dto.getPassword())
                .name(dto.getName())
                .birth(birth)
                .email(email)
                .phone(dto.getPhone()).build();


        //3. 비밀번호 암호화
        user.encodePassword(passwordEncoder);

        userRepository.save(user);
    }

//    @Transactional(readOnly = true)
//    public void login(LoginRequestDto dto) {
//        //1. 아이디 존재 확인
//        User user = userRepository.findById(dto.getUser_id()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
//
//
//        //2. 아이디에 대한 정보 비밀번호가 맞는지 확인
//        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
//            //예외 발생
//            throw new CustomException(DUPLICATE_USER_ID);
//        }
//
//        //로그인 완료
//
//
//    }

    @Transactional(readOnly = true)
    public InformationResponseDto getInfo(String user_id){
        User user = userRepository.findById(user_id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        //if(user==null) throw new CustomException(USER_NOT_FOUND);

        //Birth 값 받기

        return InformationResponseDto.builder()
                .user_id(user_id)
                .name(user.getName())
                .birth(new BirthResponse(user.getBirth().getYear(), user.getBirth().getMonth(), user.getBirth().getDay()))
                .phone(user.getPhone())
                .email(user.getEmail().getValue())
                .level(user.getLevel())
                .build();

    }

    @Transactional
    public InformationResponseDto changeInfo(String user_id, ChangeInfoRequestDto dto){
        User user = userRepository.findById(user_id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        //이메일 값 변경해주기
        Email email = Email.of(dto.getEmail());

        //birth 바꾸기
        Birth birth = Birth.of(dto.getBirth().getYear(), dto.getBirth().getMonth(), dto.getBirth().getDay());

        user.changeInfo(dto.getName(), birth, dto.getPhone(), email);

        return InformationResponseDto.builder()
                .name(user.getName())
                .birth(new BirthResponse(user.getBirth().getYear(), user.getBirth().getMonth(), user.getBirth().getDay()))
                .phone(user.getPhone())
                .email(user.getEmail().getValue())
                .build();
    }

    @Transactional
    public void changePassword(String user_id, ChangePasswordRequestDto dto){
        //아이디로 user가져오기
        User user = userRepository.findById(user_id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        //user의 password와 비교하기

        //=== 순서 중요
        if(!passwordEncoder.matches(dto.getNow_password(), user.getPassword())) throw new CustomException(DUPLICATE_USER_ID);
        //일치할 경우 비밀번호 변경
        user.changePassword(dto.getNew_password());
        user.encodePassword(passwordEncoder);
        userRepository.save(user);
    }

    public void deleteUser(String user_id){

        userRepository.deleteById(user_id);
    }

}
