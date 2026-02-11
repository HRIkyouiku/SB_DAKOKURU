package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.TimestampViewDTO;
import com.example.demo.dto.UserDailyViewDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public Optional<User> findById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public void deleteById(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByEmployeeNo(Integer employeeNo) {
		return userRepository.findByEmployeeNo(employeeNo);
	}

    @Override
    public List<Object[]> getDailyTimestamps(List<Long> userIds, LocalDate startDate, LocalDate endDate) {
    	List<Long> param = (userIds == null || userIds.isEmpty()) ? null : userIds;
        //if (userIds == null || userIds.isEmpty()) {
        //    return Collections.emptyList();
        //}

        List<Object[]> results = userRepository.findDailyTimestamps(param, startDate, endDate);

        return results;
    }
    
    @Override
    public List<UserDailyViewDTO> getDailyTimestampsView(List<Long> userIds, LocalDate startDate, LocalDate endDate
    ) {

        List<Object[]> rows = getDailyTimestamps(userIds, startDate, endDate);

        List<UserDailyViewDTO> result = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        for (Object[] row : rows) {

            UserDailyViewDTO view = new UserDailyViewDTO();
            view.setUserId(((Number) row[0]).longValue());
            view.setName(row[1] + " " + row[2]);
            view.setDepartmentName((String) row[3]);

            String json = row[4] == null ? "[]" : row[4].toString();

            try {
                JsonNode root = mapper.readTree(json);

                for (JsonNode dayNode : root) {
                	 JsonNode dateNode = dayNode.get("date");
                	    if (dateNode == null || dateNode.isNull()) {
                	        continue;
                	    }

                    LocalDate date = LocalDate.parse(dayNode.get("date").asText());

                    List<TimestampViewDTO> list = new ArrayList<>();

                    for (JsonNode ts : dayNode.get("timestamps")) {
                    	
                        TimestampViewDTO tv = new TimestampViewDTO();
                        
                        tv.setTime(LocalTime.parse(ts.get("time").asText()));
                        tv.setType(ts.get("type").asText());
                        list.add(tv);
                    }
                    view.getDailyMap().put(date, list);
                }
            } catch (Exception e) {
                throw new RuntimeException("JSON parse error", e);
            }
            result.add(view);
        }

        return result;
    }
}
