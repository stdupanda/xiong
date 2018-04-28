package proxy;

public class UserServiceImp2 implements UserService {

	@Override
	public String getNameById(Integer userId) {
		System.out.println("getNameById2" + userId);
		return "name2" + userId;
	}

	@Override
	public String getAgeById(Integer userId) {
		System.out.println("getAgeById2" + userId);
		return "age2" + userId;
	}

}
