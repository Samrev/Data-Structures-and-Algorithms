// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    BSTree left;
    public BSTree right; // Children.
    public BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }
    private BSTree getroot()
    {
        BSTree current = this;
        if(current.parent ==null)
            return current.right;
        while(current.parent.parent!=null)
        {
            current = current.parent;
        }
        return current;
        
    }
    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree current = this.getroot();
        if(current==null)
        {
            BSTree newnode = new BSTree(address,size,key);
            this.right = newnode;
            newnode.parent = this;
            return newnode;
        }
        BSTree temp=null;
        boolean isLeft=false;
        while(current!=null)
        {
         temp=current;
         if(current.key<key)
         {
             isLeft = false;
             current = current.right;
         }
         else if(current.key>key)
         {
             isLeft=true;
             current = current.left;
         }
             
         else
         {
             if(current.address<address)
             {
                 int temp1 = current.address;
                 current.address = address;
                 address = temp1;
                 current = current.left;
                 isLeft = true;
             }
           
             else if(current.address>address)
             {
                 isLeft = true;
                 current = current.left;
             }
             else
                 return null;
         }
        }
        BSTree newnode = new BSTree(address,size,key);
        if(isLeft)
        {
            temp.left = newnode;
            newnode.parent = temp;
        }
        else
        {
            temp.right = newnode;
            newnode.parent = temp;
        }
        return newnode;
    }

    public boolean Delete(Dictionary e)
    {
        BSTree current = this.getroot();
        if(current==null)
            return false;
        while(current!=null)
        {
            if(current.key==e.key)
            {        
                if(current.address==e.address&&current.size==e.size)
                { 
                    if(current.left==null&&current.right==null)
                    {
                        if(current.parent.left==current)
                        {
                            current.parent.left=null;
                            current.parent=null;
                        }
                        else
                        {
                            current.parent.right=null;
                            current.parent = null;
                        }
                    }
                    else if(current.left!=null&&current.right!=null) 
                    {
                        System.out.println(current.left.address);
                        BSTree succ = current.right;
                        while(succ.left!=null)
                            succ = succ.right;
                        current.key = succ.key;current.address = succ.address;
                        current.size=succ.size;
                        if(succ.left==null&&succ.right==null)
                        {
                            if(succ.parent.left==current)
                            {
                                succ.parent.left=null;
                                succ.parent=null;
                            }
                            else
                            {
                                succ.parent.right=null;
                                succ.parent = null;
                            }
                        }
                        else if(succ.left!=null||succ.right!=null)
                        {
                            BSTree temp2 = null,temp1 = succ.parent;
                            if(succ.left!=null)
                            {
                                temp2 = succ.left;
                                succ.left=null;
                            }
                            if(succ.right!=null)
                            {
                                temp2 = succ.right;
                                succ.right=null;
                            }
                            if(succ.parent.left==current)
                                temp1.left = temp2;
                            else
                                temp1.right =temp2;
                            temp2.parent = temp1;
                            succ.parent=null;
                        }   
                    }
                    else
                    {
                        BSTree temp2 = null,temp1 = current.parent;
                        if(current.left!=null)
                        {
                            temp2 = current.left;
                            current.left=null;
                        }
                        if(current.right!=null)
                        {
                            temp2 = current.right;
                            current.right=null;
                        }
                        if(current.parent.left==current)
                            temp1.left = temp2;
                        else
                            temp1.right =temp2;
                        temp2.parent = temp1;
                       current.parent=null;
                    }
                    
                    //System.out.println(current.left.address);
                    return true;
                }
                current = current.left;
            }           
        
            if(current.key>=e.key)
                current=current.left;
            if(current.key<e.key)
                current = current.right;
        }
        return false;
    }
    
    public BSTree Find(int key, boolean exact)
    {
        BSTree current = this.getroot();
        if(current==null)
            return null;
        if(exact)
        {
            while(current!=null)
            {
                if(current.key == key)
                {
                    BSTree temp = current;
                     while(current.left!=null&&current.left.key==key)
                     {
                         if(current.address<temp.address)
                             temp=current;
                         current=current.left;
                     }
                     return temp;
                }
                else if(current.key<key)
                    current = current.right;
                else
                    current = current.left;            
            }
            return null;
        }
        else
        {
            while(current!=null)
            {
                if(current.key==key)
                {
                     BSTree temp = current;
                     while(current.left!=null&&current.left.key==key)
                     {
                         if(current.address<temp.address)
                             temp=current;
                         current=current.left;
                     }
                     return temp;
                }    
                else if(current.key<key)
                    current = current.right;
                else
                {
                    BSTree temp = current.left;
                    int k = current.key;
                    if(temp!=null)
                    {
                        while(temp.right!=null)
                            temp = temp.right;
                        if(temp.key>=key)
                            current = temp;
                    }
                    BSTree temp1 = current;
                    while(current.left!=null&&current.left.key==k)
                    {
                        if(current.address<temp1.address)
                            temp1 = current;
                        current = current.left;
                    }
                    return temp1;
                }
            }
        }
        return null;
    }

    public BSTree getFirst()
    {
        BSTree current = this;
        if(current.parent==null)
            current =current.right;
        if(current==null)
            return null;
        while(current.left!=null)
            current = current.left;
        BSTree temp = current;
//        int key = temp.key;
//        while(current!=this&&current.parent.key==key)
//        {
//            if(current.parent!=null&&current.address<temp.address)
//                temp = current;
//            current=current.parent;
//        }
//        if(temp.parent!=null)
            return temp;
        //return null;
    }

    public BSTree getNext()
    { 
        BSTree current = this;
        if(current.parent==null)
            return current.getFirst();
        if(current.right!=null)
        {
            current = current.right;
            while(current.left!=null)
                current = current.left;
            BSTree temp = current;
//            int key = current.key;
//            while(current.parent!=this&&current.parent.key==key)
//            {
//                if(current.address<temp.address)
//                    temp = current;
//                current = current.parent;
//            }
            return temp;
        }
        else
        {
            BSTree temp1 = current.parent;
            if(temp1!=null)
            {
                while(temp1.parent!=null&&temp1.left!=current)
                {
                    current = temp1;
                    temp1 = current.parent;
                }
            
//            if(temp1.parent!=null && temp1.key==current.key)
//            {
//                BSTree temp2 = current;
//                while(temp1.parent!=null&&temp1.key==temp2.key)
//                {
//                    if(temp1.address<temp2.address)
//                        temp2=temp1;
//                    temp1 = temp1.parent;
//                }
//                return temp2;
//            }
            }
            if(temp1.parent==null)
                return null;
            return temp1;
        }
        //return null;
    }

    public boolean sanity()
    { 
        BSTree i;
        for(i=this.getFirst();i!=null;i=i.getNext())
        {
            if((i.left!=null&&i.left.parent!=i)||(i.right!=null&&i.right.parent!=i))
                return false;
        }
        
        
        return true;
    }
}


 


