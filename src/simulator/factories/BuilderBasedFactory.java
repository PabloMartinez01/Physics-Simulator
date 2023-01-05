package simulator.factories;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	
	List<Builder<T>> builders;
	List<JSONObject> factoryElements;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this.builders = new ArrayList<>(builders);
		factoryElements = new ArrayList<JSONObject>();
		
		for (Builder<T> b : builders) {
			factoryElements.add(b.getBuilderInfo());
		}
	}

	@Override
	public T createInstance(JSONObject info) {
		if(info == null) throw new IllegalArgumentException("Invalid value for createInstance: null");
		for (Builder<T> b : builders) {
			T object = b.createInstance(info);
			if (object != null) return object;
		}
		throw new IllegalArgumentException();
	}

	@Override
	public List<JSONObject> getInfo() {
		return factoryElements;
	}

}
