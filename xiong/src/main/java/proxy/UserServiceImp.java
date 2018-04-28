package proxy;

public class UserServiceImp implements UserService {

	@Override
	public String getNameById(Integer userId) {
		System.out.println("getNameById" + userId);
		return "name" + userId;
	}

	@Override
	public String getAgeById(Integer userId) {
		System.out.println("getAgeById" + userId);
		return "age" + userId;
	}

}
