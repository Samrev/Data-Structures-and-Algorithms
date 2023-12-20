// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
		A1List newnode = new A1List(address,size,key);
		newnode.prev = this;
		newnode.next = this.next;
		this.next.prev = newnode;
		this.next = newnode;
        return newnode;
    }

    public boolean Delete(Dictionary d) 
    {
		A1List current1 = this;
                A1List current2 = this.prev;
                if(current2!=null)
                {
                    while((current1.next!=null)&&(current2.prev!=null))
		    {
			if(d.key==current1.key)
			{
			        if(d==current1)
				{   
                                    current1.prev.next=current1.next;
				    current1.next.prev = current1.prev;
				    current1.prev=null;current1.next=null;current1=null;
				    return true;
				}
			}
			current1=current1.next;
                        if(d.key==current2.key)
                        {
                            if(d==current2)
                            {
                                current2.prev.next = current2.next;
                                current2.next.prev = current2.prev;
                                current2.prev=null;current2.next=null;current2=null;
                                return true;
                            }
                        }
                        current2 = current2.prev;
		}
                }
		
                if(current2!=null)
                {
                    while(current2.prev!=null)
                    {
                        if(current2.key==d.key)
                        {
                            if(current2==d)
                            {
                                current2.prev.next=current2.next;
		                current2.next.prev = current2.prev;
		                current2.prev=null;current2.next=null;current2=null;
		                return true;  
                            }
                        }
                        current2=current2.prev;
                    }

                }
                while(current1.next!=null)
                {
                    if(current1.key==d.key)
                    {
                        if(current1==d)
                        {
                            current1.prev.next=current1.next;
		            current1.next.prev = current1.prev;
		            current1.prev=null;current1.next=null;current1=null;
		            return true;
                        }
                    }
                }
		return false;
		
    }
    public A1List Find(int k, boolean exact)
    { 
	    A1List current1 = this.getFirst();
            if(current1!=null&&exact)
            {
                while(current1.next!=null)
               {
                   if(current1.key==k)
                       return current1;
                   current1 = current1.next;
               }
               return null;
            }
            else if(current1!=null)
            {
               while(current1.next!=null)
               {
                   if(current1.key>=k)
                       return current1;
                   current1 = current1.next;
               }
               return null;
            }
            return null;
            
    }
    
    public A1List getFirst()
    {
        A1List current = this;
        while(current.prev!=null)
        {
            current = current.prev;
        }
        if(current.next.next!=null)
            return current.next;
        return null;
    }
    public A1List getNext() 
    {
        if(this.next!=null&&this.next.next!=null)
            return this.next;
        return null;
    }

    public boolean sanity()
    {
        A1List start=this.getFirst();
        if(start!=null&&start.prev.prev!=null)
            return false;
        if(start==null&&(this.prev!=null&&this.next!=null))
            return false; 
        if(start==null&&this.next==null&&this.prev.prev!=null)
            return false;
        if(start==null&&this.prev==null&&this.next.next!=null)
            return false;
            
        A1List i,end = null;
        for(i=start;i!=null;i=i.getNext())
        {
            if(i.next.prev!=i || i.size<=0 && i.address<0)
                return false;
            end = i;
        }
        if(start!=null&& (end.next.next!=null))
            return false;
        return true;
        
    }

}


