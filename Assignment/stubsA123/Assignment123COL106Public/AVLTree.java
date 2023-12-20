// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    private AVLTree getroot()
    {
        AVLTree current = this;
        if(current.parent ==null)
            return current.right;
        while(current.parent.parent!=null)
        {
            current = current.parent;
        }
        return current;
        
    }
    private void LeftRotate()
    {
        AVLTree y = this.right;
        AVLTree z = this;
        y.parent = z.parent;
        
        if(z.parent!= null)
        {
            if(z.parent.right==z)
                z.parent.right=y;
            else
                z.parent.left = y;
        }
        z.parent = y;
        z.right = y.left;
        if(z.right!=null)
            z.right.parent = z;
        y.left = z;
        z.height =z.height-2;
    }
    private void RightRotate()
    {
        AVLTree z = this;
        AVLTree y = this.left;
        y.parent = z.parent;
        if(z.parent!=null)
        {
            if  (z.parent.right==z)
                z.parent.right = y;
        
            else
                z.parent.left = y;
        }
        z.parent = y;
        z.left = y.right;
        if(z.left != null)
            z.left.parent = z;
        y.right = z;
        z.height=z.height-2;
        
    }
    private void RightLeftRotate()
    {
        AVLTree y = this.left;
        AVLTree x = y.right;
        AVLTree z = this;
        y.LeftRotate();
        z.RightRotate();
    }
    private void LeftRightRotate()
    {
        AVLTree y = this.right;
        AVLTree x = y.left;
        AVLTree z = this;
        y.RightRotate();
        z.LeftRotate();
    }
    private void Balance()
    {
        AVLTree z = this,y;
        if(z.left!=null&&z.right!=null)
        {
            if(z.left.height>z.right.height)
            {
                y = z.left;
                if(y.left!=null&&y.right!=null)
                {
                    if(y.left.height>y.right.height)
                    {
                        z.RightRotate();
                    }
                    else
                    {
                        z.RightLeftRotate();
                    }
                }
                else if(y.left!=null)
                {
                    z.RightRotate();
                }
                else if(y.right!=null)
                {
                    z.RightLeftRotate();
                }
            }
            else
            {
                y=z.right;
                if(y.left!=null&&y.right!=null)
                {
                    if(y.left.height>y.right.height)
                        z.LeftRightRotate();
                    else
                    {
                        z.LeftRotate();
                    }
                }
                else if (y.left!=null)
                {
                    z.LeftRightRotate();
                }    
                else if(y.right!=null)
                {
                    z.LeftRotate();
                }
            }
        }
        else if(z.left!=null)
        {
            y = z.left;
            if(y.left!=null&&y.right!=null)
            {
                if(y.left.height>y.right.height)
                {
                    z.RightRotate();
                }
                else
                {
                    z.RightLeftRotate();
                }
            }
            else if(y.left!=null)
            {
                z.RightRotate();
            }
            else if(y.right!=null)
            {
                z.RightLeftRotate();
            }
        }
        else if(z.right!=null)
        {
            y=z.right;
            if(y.left!=null&&y.right!=null)
            {
                if(y.left.height>y.right.height)
                z.LeftRightRotate();
                else
                {
                    z.LeftRotate();
                }
            }
            else if (y.left!=null)
            {
                z.LeftRightRotate();
            }    
            else if(y.right!=null)
            {
                //System.out.println(2);
                z.LeftRotate();
            }
        }
    }
        
    
    public AVLTree Insert(int address, int size, int key) 
    { 
        AVLTree current = this.getroot();
        if(current==null)
        {
            AVLTree newnode = new AVLTree(address,size,key);
            this.right = newnode;
            newnode.parent = this;
            this.height = 1;
            return newnode;
        }
        AVLTree temp=null;
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
                 current = current.right;
                 isLeft = false;
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
        AVLTree newnode = new AVLTree(address,size,key);
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
        AVLTree temp1 = newnode.parent;
        AVLTree temp2 = temp1;
        while(temp1.parent!=null)
        {
            int left_height=0,right_height=0;
            if(temp1.left!=null)
                left_height = temp1.left.height;
            if(temp1.right!=null)
                right_height = temp1.right.height;
            temp1.height=1+Math.max(left_height,right_height);
            temp1=temp1.parent;
        }
        current = newnode;
        while(current.parent!=null)
        {
            if(current.left!=null&&current.right!=null)
            {
                if((current.left.height-current.right.height)>1||(current.right.height-current.left.height)>1)
                {
                    current.Balance();
                    while(temp2.parent!=null)
                    {
                        int left_height=0,right_height=0;
                        if(temp2.left!=null)
                            left_height = temp2.left.height;
                        if(temp2.right!=null)
                            right_height = temp2.right.height;
                        temp2.height=1+Math.max(left_height,right_height);
                        temp2=temp2.parent;
                    }
                    return newnode;
                }
                
            }
            else if(current.left!=null)
            {
                if(current.left.height>0)
                {
                    current.Balance();
                    while(temp2.parent!=null)
                    {
                        int left_height=0,right_height=0;
                        if(temp2.left!=null)
                            left_height = temp2.left.height;
                        if(temp2.right!=null)
                            right_height = temp2.right.height;
                        temp2.height=1+Math.max(left_height,right_height);
                        temp2=temp2.parent;
                    }
                    return newnode;
                }
            }
            else if(current.right!=null)
            {
                if(current.right.height>0)
                {
                    current.Balance();
                    while(temp2.parent!=null)
                    {
                        int left_height=0,right_height=0;
                        if(temp2.left!=null)
                            left_height = temp2.left.height;
                        if(temp2.right!=null)
                            right_height = temp2.right.height;
                        temp2.height=1+Math.max(left_height,right_height);
                        temp2=temp2.parent;
                    }
                    return newnode;
                }
            }
            current=current.parent;
        }
        return newnode;
    }

    public boolean Delete(Dictionary e)
    {
        AVLTree current = this.getroot();
        if(current==null)
            return false;
        while(current!=null)
        {
            if(current.key==e.key)
            {        
                if(current.address==e.address&&current.size==e.size)
                {
                    AVLTree temp3;
                    if(current.left==null&&current.right==null)
                    {
                        temp3 = current.parent;
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
                        AVLTree succ = current.right;
                        while(succ.left!=null)
                            succ = succ.left;
                        temp3 = succ.parent;
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
                            AVLTree temp2 = null,temp1 = succ.parent;
                            if(succ.left!=null)
                            {
                                temp2 = succ.left;
                                succ.left=null;
                            }
                            else if(succ.right!=null)
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
                        temp3=current.parent;
                        AVLTree temp2 = null,temp1 = current.parent;
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
                    current = temp3;
                    AVLTree temp4 =temp3;
                    while(temp3.parent!=null)
                    {
                        if(temp3.left==null&&temp3.right==null)
                        {
                            temp3.height=0;
                            temp3=temp3.parent;
                            continue;
                        }
                        int left_height=0,right_height=0;
                        if(temp3.left!=null)
                            left_height = temp3.left.height;
                        if(temp3.right!=null)
                            right_height = temp3.right.height;
                        temp3.height=1+Math.max(left_height,right_height);
                        temp3 = temp3.parent;
                    }
                    while(current.parent!=null)
                    {
                        if(current.left!=null&&current.right!=null)
                        {
                            if((current.left.height-current.right.height)>1||(current.right.height-current.left.height)>1)
                            {
                                current.Balance();
                                while(temp4.parent!=null)
                                {
                                    if(temp4.left==null&&temp4.right==null)
                                    {
                                        temp4.height=0;
                                        temp4=temp4.parent;
                                        continue;
                                    }
                                    int left_height=0,right_height=0;
                                    if(temp4.left!=null)
                                        left_height = temp4.left.height;
                                    if(temp4.right!=null)
                                        right_height = temp4.right.height;
                                    temp4.height=1+Math.max(left_height,right_height);
                                    temp4 = temp4.parent;
                                }
                            }
                        }
                        else if(current.left!=null)
                        {
                            if(current.left.height>0)
                            {
                                current.Balance();
                                while(temp4.parent!=null)
                                {
                                    if(temp4.left==null&&temp4.right==null)
                                    {
                                        temp4.height=0;
                                        temp4=temp4.parent;
                                        continue;
                                    }
                                    int left_height=0,right_height=0;
                                    if(temp4.left!=null)
                                        left_height = temp4.left.height;
                                    if(temp4.right!=null)
                                        right_height = temp4.right.height;
                                    temp4.height=1+Math.max(left_height,right_height);
                                    temp4 = temp4.parent;
                                }
                            }
                        }
                        else if(current.right!=null)
                        {
                            if(current.right.height>0)
                            {
                                current.Balance();
                                while(temp4.parent!=null)
                                {
                                    if(temp4.left==null&&temp4.right==null)
                                    {
                                        temp4.height=0;
                                        temp4=temp4.parent;
                                        continue;
                                    }
                                    int left_height=0,right_height=0;
                                    if(temp4.left!=null)
                                        left_height = temp4.left.height;
                                    if(temp4.right!=null)
                                        right_height = temp4.right.height;
                                    temp4.height=1+Math.max(left_height,right_height);
                                    temp4 = temp4.parent;
                                }
                            }
                        }
                        current=current.parent;
                    }
                    return true;
                }
                else if(current.address<e.address)
                    current = current.right;
                else if(current.address>e.address)
                    current = current.left;
            }           
        
            if(current.key>e.key)
                current=current.left;
            if(current.key<e.key)
                current = current.right;
        }
        return false;
    }
        
    public AVLTree Find(int key, boolean exact)
    {
        AVLTree current = this.getroot();
        if(current==null)
            return null;
        if(exact)
        {
            while(current!=null)
            {
                if(current.key == key)
                {
                    while(current.left!=null&&current.left.key==key)
                        current=current.left;
                    return current;
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
                    while(current.left!=null&&current.left.key==key)
                        current=current.left;
                    return current;
                }    
                else if(current.key<key)
                    current = current.right;
                else
                {
                    int k = current.key;
                    while(current.left!=null&&current.left.key==k)
                        current=current.left;
                    while(current.left!=null&&current.left.key>=key)
                        current=current.left;
                    AVLTree temp = current.left;
                    while(temp!=null&&temp.right!=null)
                        temp=temp.right;
                    if(temp!=null&&temp.key>=key)
                        current = temp;
                    k = current.key;
                    while(current.left!=null&&current.left.key==k)
                        current=current.left;
                    return current;
                }
            }
        }
        return null;
    }

    public AVLTree getFirst()
    {
        AVLTree current = this;
        if(current.parent==null)
            current =current.right;
        if(current==null)
            return null;
        while(current.left!=null)
            current = current.left;
        AVLTree temp = current;
            return temp;
    }

    public AVLTree getNext()
    { 
        AVLTree current = this;
        if(current.parent==null)
            return current.getFirst();
        if(current.right!=null)
        {
            current = current.right;
            while(current.left!=null)
                current = current.left;
            return current;
        }
        else
        {
            AVLTree temp1 = current.parent;
            if(temp1!=null)
            {
                while(temp1.parent!=null&&temp1.left!=current)
                {
                    current = temp1;
                    temp1 = current.parent;
                }   
            }
            if(temp1.parent==null)
                return null;
            return temp1;
        }
    }
    private boolean IsBST()
    { 
         AVLTree i=this;
         if(i.left==null&&i.right==null)
            return true;
         if(i.left==null)
         {
             if(i.right.key>i.key||(i.right.key==i.key&&i.right.address>i.address))
                return i.right.IsBST();
             return false;
         }
         if(i.right==null)
         {
             if(i.left.key<i.key||(i.left.key==i.key&&i.left.address<i.address))
                return i.left.IsBST();
             return false;
         }
         if(i.left.key<i.key||(i.left.key==i.key&&i.left.address<i.address))
         {
             if(i.right.key>i.key||(i.right.key==i.key&&i.right.address>i.address))
             {
                 if(i.left.IsBST()&&i.right.IsBST())
                     return true;
                 return false;
             }
             return false;
         }
         return false;
    }
    
//    private int total_nodes()
//    {
//        AVLTree current = this;
//        if(current.left==null&&current.right==null)
//            return 1;
//        if(current.left==null)
//            return 1+current.right.total_nodes();
//        if(current.right==null)
//            return 1+current.left.total_nodes();
//        return 1+current.left.total_nodes()+current.right.total_nodes();
 //   }
    public boolean sanity()
    { 
        AVLTree i;
        for(i=this.getFirst();i!=null;i=i.getNext())
        {
            if((i.left!=null&&i.left.parent!=i)||(i.right!=null&&i.right.parent!=i))
                return false;
            if(i.left!=null&&i.right!=null)
            {
                if((i.left.height-i.right.height)>1||(i.right.height-i.left.height)>1)
                    return false;
            }
            if(i.right!=null&&i.left==null)
            {
                if(i.right.height>0)
                    return false;
            }
            if(i.left!=null&&i.right==null)
            {
                if(i.left.height>0)
                    return false;
            }
        }
        i=this.getroot();
        if(!i.IsBST())
            return false;
        return true;
    }

}


