public class LinkStrand implements IDnaStrand{
    private class Node{
        private String info;
        private Node next;
        private Node(String info){
            this.info = info;
            this.next = null;
        }
    }

    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private Node myCurrent;
    private int myLocalIndex;

    public LinkStrand(){
        this("");
    }
    public LinkStrand(String a){
        initialize(a);
    }

    @Override
    public long size() {
        return mySize;
    }

    @Override
    public void initialize(String source) {
        myFirst = new Node(source);
        myLast = myFirst;
        mySize = myFirst.info.length();
        myAppends = 0;
        myIndex = 0;
        myLocalIndex = 0;
        myCurrent = myFirst;
    }

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    @Override
    public IDnaStrand append(String dna) {
        myLast.next = new Node(dna);
        myLast = myLast.next;
        mySize += myLast.info.length();
        myAppends++;
        return this;
    }

    @Override
    public IDnaStrand reverse() {
        LinkStrand reversed = new LinkStrand();
        Node head = myFirst;
        
        while(head != null){
            StringBuilder a = new StringBuilder(head.info);
            String revers = a.reverse().toString();

            Node temp = new Node(revers);
            temp.next = reversed.myFirst;
            reversed.mySize += revers.length();
            reversed.myFirst = temp;

            head = head.next;
        }
        return reversed;
    }

    @Override
    public int getAppendCount() {
        return myAppends;
    }

    public String toString(){
        StringBuilder myInfo = new StringBuilder();
        Node head = myFirst;
        while(head.next != null){
            myInfo.append(head.info);
            head = head.next;
        }
        myInfo.append(head.info);
        return myInfo.toString();
    }

    @Override
    public char charAt(int index) {
        if(index < 0 || this.size() <= index){
            throw new IndexOutOfBoundsException();
        }
        if(index <= myIndex){
            myIndex = 0;
            myLocalIndex = 0;
            myCurrent = myFirst;
        }
        while(index != myIndex){
            myIndex ++;
            myLocalIndex ++;
            if(myCurrent.next != null && myCurrent.info.length() <= myLocalIndex){
                myCurrent = myCurrent.next;
                myLocalIndex = 0;
            }
        }
        return myCurrent.info.charAt(myLocalIndex);
    }

}