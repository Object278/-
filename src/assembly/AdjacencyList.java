package assembly;

import java.util.ArrayList;
import java.util.HashMap;

import component.Linkable;

public class AdjacencyList implements ModuleStorage {
	//ר�������洢����ͼ���ڽӱ�
	//n�Ƕ�������m�Ǳߵ�����
	private int n=0, m=0;
	boolean directed = false;
	ArrayList<ArrayList<Integer>> list;
	HashMap<Integer, Linkable> numToModule;
	boolean isProxy;
	
	public class AdjIterator{
		private AdjacencyList adjList;
		int v;//v�Ǳ������Ľڵ�
		int index;//��ǰ�������Ľڵ�
		
		public AdjIterator(AdjacencyList adjList, int v) {
			this.adjList=adjList;
			this.v=v;
			this.index=0;
		}
		
		public int begin() {
			//ÿ�ε���begin��ʱ��index��Ҫ����
			index=0;
			if(adjList.list.get(v).size() != 0) {
				return (int)adjList.list.get(v).get(index);
			}
			return -1;
		}
		
		public int next() {
			index++;
			if( index<adjList.list.get(v).size() ) {;
				return (int)adjList.list.get(v).get(index);
			}
			return -1;
		}
		
		public boolean end() {
			return index >= adjList.list.get(v).size();
		}
	}
	
	public AdjacencyList() {
		this.isProxy = false;
		list=new ArrayList<>(512);
		numToModule = new HashMap<>();
	}
	
	/*
	 * Wenn you want to create a Proxy of this class, you can set is Proxy to true;
	 */
	public AdjacencyList(boolean isProxy) {
		if(isProxy) {
			list = null;
			numToModule = null;
		}else {
			this.isProxy = isProxy;
			list=new ArrayList<>(512);
			numToModule = new HashMap<>();
		}
	}
	
	@Override
	public int numOfModules() {
		if(isProxy) {
			return 0;
		}
		return n;
	}
	
	@Override
	public int edge() {
		if(isProxy) {
			return 0;
		}
		return m;
	}
	
	@Override
	//��ʾv��w�ڵ�������
	//��Ҫ����������޸�Ϊ��ӵ�ʱ�����ָ�����ĸ��棨�������
	//0-x������1-x������2-y������3-y������4-z������5-z������
	//��Щģ��ֻ����������������
	public void link(int v, int w, int direction) {
		if(isProxy) {
			return;
		}
		if(v<0 || w<0 ) {
			throw new IllegalArgumentException("Parameters must be bigger than 0.");
		}
		if(v>=n || w>=n) {
			throw new IllegalArgumentException("The number of Modules is too much. What r u doing?");
		}
		if(!numToModule.containsKey(v) || !numToModule.containsKey(w)) {
			throw new IllegalArgumentException("Please create the Module and add it to numToModule by using addModule()");
		}
		if(list.get(v) == null) {
			list.set(v, new ArrayList<Integer>(numToModule.get(v).getLinkNumber()));
		}
		//��ģ�������в������Ի��ߣ��Լ������Լ�����ƽ�бߣ�һ���������Ӷ������ģ�飩
		//����Ĵ���ʽ���������Ч������������ɱ���Ҳ����
		//����ģ����������ͼ�洢
		if(!list.get(v).contains(w) && v != w && !directed) {
			list.get(v).set(direction, w);
			list.get(w).set(direction, w);
			m++;
		}
	}
	
	public void addModule(int id, Linkable module) {
		if(isProxy) {
			return;
		}
		if(numToModule.containsKey(id)) {
			throw new IllegalArgumentException("One ID can only map to one module");
		}
		numToModule.put(id, module);
	}
	
	public boolean hasEdge(int v, int w) {
		if(isProxy) {
			return false;
		}
		if(v<0 || v>=n || w<0 || w>=n) {
			throw new IllegalArgumentException("Error");
		}
		for(int i=0; i<list.get(v).size(); i++) {
			if((int)list.get(v).get(i) == w) {
				return true;
			}
		}
		return false;
	}
	
	public void show() {
		if(isProxy) {
			return;
		}
		for(int i=0; i<n; i++) {
			System.out.print("Module"+i+": ");
			for(int j=0; j<list.get(i).size(); j++) {
				System.out.print(list.get(i).get(j)+" ");
			}
			System.out.println();
		}
	}
	
	public HashMap<Integer, Linkable> getNumToModule() {
		return numToModule;
	}

	public boolean isProxy() {
		return isProxy;
	}

	public static void main(String[] args) {
		
	}

}
