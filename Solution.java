class Solution {
    public int mostBooked(int n, int[][] meetings) {
        PriorityQueue<Integer> p=new PriorityQueue<Integer>((o1,o2)->Integer.compare(o1,o2));
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<meetings.length;i++){
            int[] mi=meetings[i];
            int start=mi[0];
            int end=mi[1];
            map.put(start,end);

            p.offer(start);
        }
        long[][] nstate=new long[n][4];
        init(nstate);
        while(!p.isEmpty()){
            int task=p.poll();
            int rest=hasrest(nstate);
            int up=hasup(nstate,task);
            if(up!=-1){
                int nextroom=up;
                nstate[nextroom][0]=task;
                nstate[nextroom][1]=task;
                nstate[nextroom][2]=map.get(task);
                nstate[nextroom][3]+=1;


            }
            else{
                if(rest==-1){
                    int nextroom=getnext(nstate,task);
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
        }
        ArrayList<Integer> list =new ArrayList<>();
        long ans=Long.MIN_VALUE;
        for(int k=0;k<nstate.length;k++){
            long[] nk=nstate[k];
            long num=nk[3];
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
    public static void init(long[][] nstate){
        for(int i=0;i<nstate.length;i++){
            long[] ni=nstate[i];
            ni[0]=-1l;
        }
    }

    public static int hasrest(long[][] nstate){
        for(int i=0;i<nstate.length;i++){
            long[] ni=nstate[i];
            long temp=ni[0];
            if(temp==-1l){
                return i;
            }
        }
        return -1;
    }

    public static int getnext(long[][] nstate, int task){
        long ans=Long.MAX_VALUE;
        int result=-1;
        for(int i=0;i<nstate.length;i++){
            long[] ni=nstate[i];
            long temp=ni[2];
            if(temp<ans){
                ans=temp;
                result=i;
            }
        }
        return result;
    }
    public static int hasup(long[][] nstate,int task){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0;i<nstate.length;i++){
            long[] ni=nstate[i];
            long temp=ni[2];
            if(temp<=task){
                list.add(i);
            }
        }
        Collections.sort(list);
        if(list.isEmpty()){
            return -1;
        }
        else{
            return list.get(0);
        }
    }
}