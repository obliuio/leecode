
import java.util.*;

class SolutionOrigin {
    public int mostBooked(int n, int[][] meetings) {
        PriorityQueue<Integer> p=new PriorityQueue<>((o1, o2)->o1-o2);
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<meetings.length;i++){
            int[] mi=meetings[i];
            int start=mi[0];
            int end=mi[1];
            map.put(start,end);

            p.offer(start);
        }
        int[][] nstate=new int[n][4];
        init(nstate);
        while(!p.isEmpty()){
            int task=p.poll();
            int rest=hasrest(nstate);
            if(rest==-1){
                int nextroom=getnext(nstate);
                nstate[nextroom][0]=task;
                nstate[nextroom][1]=nstate[nextroom][2];
                nstate[nextroom][2]=nstate[nextroom][1]+map.get(task)-task;
                nstate[nextroom][3]+=1;

            }
            else{
                nstate[rest][0]=task;
                nstate[rest][1]=task;
                nstate[rest][2]=map.get(task);
                nstate[rest][3]=nstate[rest][3]+1;
            }
        }
        ArrayList<Integer> list =new ArrayList<>();
        int ans=Integer.MIN_VALUE;
        for(int k=0;k<nstate.length;k++){
            int[] nk=nstate[k];
            int num=nk[3];
            if(ans<num){
                ans=num;
                list.clear();
                list.add(k);
            }
            else if(ans==num){
                list.add(k);
            }
        }
        Collections.sort(list);
        return list.get(0);

    }
    public static void init(int[][] nstate){
        for(int i=0;i<nstate.length;i++){
            int[] ni=nstate[i];
            ni[0]=-1;
        }
    }

    public static int hasrest(int[][] nstate){
        for(int i=0;i<nstate.length;i++){
            int[] ni=nstate[i];
            int temp=ni[0];
            if(temp==-1){
                return i;
            }
        }
        return -1;
    }

    public static int getnext(int[][] nstate){
        int ans=Integer.MAX_VALUE;
        int result=-1;
        for(int i=0;i<nstate.length;i++){
            int[] ni=nstate[i];
            int temp=ni[2];
            if(temp<ans){
                ans=temp;
                result=i;
            }
        }
        return result;
    }
}